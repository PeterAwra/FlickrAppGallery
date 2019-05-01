package com.stud.awra.flickrappgallery;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
  public static final String API_KEY = "26e83e1921b2d40964324325ff109858";
  public static final String METHOD = "flickr.photos.getRecent";
  public static final String FORMAT = "json";
  public static final int NO_JSON_CALLBACK = 1;

  private RecyclerView recyclerView;
  private PhotoAdapter adapter;
  private SearchView searchView;
  private Observable<String> observableSearchView;
  private Disposable subscribe;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    searchView = findViewById(R.id.search_view);
    recyclerView = findViewById(R.id.rv);
    recyclerView.setLayoutManager(
        new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false));
    adapter = new PhotoAdapter(this);
    recyclerView.setAdapter(adapter);
    observerSearchView();
  }

  private void observerSearchView() {
    observableSearchView = Observable.create(
        emitter -> searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
          @Override public boolean onQueryTextSubmit(String s) {
            return false;
          }

          @Override public boolean onQueryTextChange(String s) {
            emitter.onNext(s);
            return false;
          }
        }));subscribe = observableSearchView
        .observeOn(Schedulers.io())
        .debounce(500, TimeUnit.MILLISECONDS)
        .map(String::trim)
        .filter(s -> s.length() >= 3)
        .distinctUntilChanged()
        .switchMap(this::flickrGetQueryPhotos)
        .map(resultFotosCall -> resultFotosCall.getPhotos().getPhoto())
        //.subscribeOn(AndroidSchedulers.mainThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(photos -> adapter.setData(photos), this::showSnackbar);
  }

  private void showSnackbar(Throwable throwable) {
    Snackbar.make(searchView, throwable.toString(), Snackbar.LENGTH_SHORT).show();
    observerSearchView();
  }

  @Override protected void onDestroy() {
    super.onDestroy();
  }

  public Observable<ResultFotos> flickrGetQueryPhotos(String query) {
    return App.getFlickrApi()
        .getQueryData("flickr.photos.search", API_KEY, FORMAT, NO_JSON_CALLBACK, query);
  }
}


