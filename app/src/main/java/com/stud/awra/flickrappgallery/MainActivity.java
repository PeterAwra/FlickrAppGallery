package com.stud.awra.flickrappgallery;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

  public static final String API_KEY = "26e83e1921b2d40964324325ff109858";
  public static final String FORMAT = "json";
  public static final String METHOD = "flickr.photos.getRecent";
  private RecyclerView recyclerView;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    recyclerView = findViewById(R.id.rv);
    recyclerView.setLayoutManager(
        new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false)
    );
    PhotoAdapter adapter = new PhotoAdapter();
    recyclerView.setAdapter(adapter);
    getPhotosOkHttp(adapter);
  }

  private void getPhotosOkHttp(final PhotoAdapter adapter) {
    final OkHttpClient client = new OkHttpClient();
    final Request request = new Request.Builder().url(
        "https://api.flickr.com/services/rest/" +
            "?method=" + METHOD +
            "&api_key=" + API_KEY +
            "&format=" + FORMAT +
            "&nojsoncallback=1")
        .get()
        .build();
    client.newCall(request).enqueue(new Callback() {
      @Override public void onFailure(Call call, IOException e) {
        showSnakBarError(e, adapter);
      }

      @Override public void onResponse(Call call, Response response) throws IOException {
        Gson gson = new Gson();
        ResultRecent resultRecent = null;
        if (response.body() != null) {
          resultRecent = gson.fromJson(response.body().string(), ResultRecent.class);
        }
        if (resultRecent != null) {
          Photos photos = resultRecent.getPhotos();
          final List<Photo> photo = photos.getPhoto();
          runOnUiThread(new Runnable() {
            @Override public void run() {
              adapter.setData(photo);
            }
          });
        }
      }
    });
  }

  private void showSnakBarError(Exception e, final PhotoAdapter adapter) {
    Snackbar.make(recyclerView, e.getLocalizedMessage(), Snackbar.LENGTH_INDEFINITE)
        .setAction("Retry", new View.OnClickListener() {
          @Override public void onClick(View v) {
            getPhotosOkHttp(adapter);
          }
        })
        .show();
  }
}
