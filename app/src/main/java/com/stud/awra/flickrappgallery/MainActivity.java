package com.stud.awra.flickrappgallery;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import com.bumptech.glide.Glide;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

  public static final String API_KEY = "26e83e1921b2d40964324325ff109858";
  public static final String FORMAT = "json";
  private RecyclerView recyclerView;
  private SearchView searchView;
  private PhotoAdapter adapter;
  private Disposable subscribeSearch;
  private Disposable subscribeRecent;
  private NestedScrollView nestedScrollView;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    recyclerView = findViewById(R.id.rv);
    searchView = findViewById(R.id.search_view);
    nestedScrollView = findViewById(R.id.scroll);
    recyclerView.setLayoutManager(
        new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false)
    );
    adapter = new PhotoAdapter();
    recyclerView.setAdapter(adapter);
    getRecent();
    observableSearchView(searchView);
    new Thread(() -> Glide.get(this).clearDiskCache()).start();
  }

  private void getRecent() {
    Thread thread = new Thread(() -> subscribeRecent = App.getApiFlickrService()
        .getRecent(ApiFlickrService.METHOD, ApiFlickrService.API_KEY, ApiFlickrService.FORMAT,
            1)
        .observeOn(Schedulers.io())
        .map(resultPhotos -> resultPhotos.getPhotos().getPhoto())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(photos -> adapter.setData(photos), this::showSnakBarError,
            () -> subscribeRecent.dispose()));
    thread.start();
  }

  private void observableSearchView(SearchView searchView) {
    Observable<String> stringObservable = Observable.create(emitter -> {
      searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
        @Override public boolean onQueryTextSubmit(String query) {
          searchView.clearFocus();
          if (query.length() < 3) {
            getRecent();
          } else {
            emitter.onNext(query);
          }
          return false;
        }

        @Override public boolean onQueryTextChange(String newText) {
          emitter.onNext(newText);
          return false;
        }
      });
    });
    subscribeSearch = stringObservable.observeOn(Schedulers.io())
        .debounce(500, TimeUnit.MILLISECONDS)
        .map(String::trim)
        .filter(s -> s.length() >= 3)
        .map(String::toLowerCase)
        //.distinctUntilChanged()
        .switchMap(this::flickrGetQueryPhotos)
        .map(resultPhotos -> resultPhotos.getPhotos().getPhoto())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(photos -> {
          //recyclerView.smoothScrollToPosition(0);
          adapter.setData(photos);
          nestedScrollView.smoothScrollTo(0, 0);
        }, throwable -> showSnakBarError(throwable, adapter));
  }

  private void showSnakBarError(Throwable e, final PhotoAdapter adapter) {
    Snackbar.make(recyclerView, e.getLocalizedMessage(), Snackbar.LENGTH_INDEFINITE)
        .setAction("Retry", v -> observableSearchView(searchView))
        .show();
  }

  private void showSnakBarError(Throwable e) {
    Snackbar.make(recyclerView, e.getLocalizedMessage(), Snackbar.LENGTH_INDEFINITE)
        .setAction("Retry", v -> getRecent())
        .show();
  }

  public Observable<ResultPhotos> flickrGetQueryPhotos(String query) {
    return App.getApiFlickrService()
        .getQueryData("flickr.photos.search", API_KEY, FORMAT, 1, query);
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    if (!subscribeRecent.isDisposed()) {
      subscribeRecent.dispose();
    }
    if (!subscribeSearch.isDisposed()) {
      subscribeSearch.dispose();
    }
  }
}
