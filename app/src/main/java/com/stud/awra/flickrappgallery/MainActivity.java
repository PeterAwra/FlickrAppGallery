package com.stud.awra.flickrappgallery;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

  private RecyclerView recyclerView;
  private PhotoAdapter adapter;
  private Handler handler;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    handler = new Handler(Looper.getMainLooper());
    recyclerView = findViewById(R.id.rv);
    recyclerView.setLayoutManager(
        new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false));
    adapter = new PhotoAdapter(this);
    recyclerView.setAdapter(adapter);
    new FlicrkGetRecentPhotos().start();
  }

  class FlicrkGetRecentPhotos extends Thread {
    public static final String API_KEY = "26e83e1921b2d40964324325ff109858";
    public static final String METHOD = "flickr.photos.getRecent";
    public static final String FORMAT = "json";
    public static final int NO_JSON_CALLBACK = 1;

    @Override public void run() {
      Call<ResultFotos> recent =
          App.getFlickrApi().getData(METHOD, API_KEY, FORMAT, NO_JSON_CALLBACK);
      recent.enqueue(new Callback<ResultFotos>() {
        @Override public void onResponse(Call<ResultFotos> call, Response<ResultFotos> response) {
          final ResultFotos body = response.body();
          handler.post(new Runnable() {
            @Override public void run() {
              adapter.setData(body != null ? body.getPhotos() : null);
            }
          });
        }

        @Override public void onFailure(Call<ResultFotos> call, Throwable t) {
          Toast.makeText(MainActivity.this,t.toString(),Toast.LENGTH_LONG).show();

        }
      });
    }
  }
}

/*try {
    String stringUrl = Uri.parse("https://api.flickr.com/services/rest/").buildUpon()
    .appendQueryParameter("method", "flickr.photos.getRecent")
    .appendQueryParameter("api_key", KEY_APP)
    .appendQueryParameter("format", "json")
    .appendQueryParameter("nojsoncallback", "1")
    .build().toString();
    URL url = new URL(stringUrl);
    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
    InputStream inputStream = url.openStream();
    char[] buffer = new char[1024];
    InputStreamReader reader = new InputStreamReader(inputStream);
    int n = 0;
    String json;
    StringWriter writer = new StringWriter();
    while ((n = reader.read(buffer)) != -1) {
    writer.write(buffer, 0, n);
    }
    Gson gson = new Gson();
final ResultFotos resulPhotos = gson.fromJson(writer.toString(), ResultFotos.class);
    handler.post(new Runnable() {
@Override public void run() {
    adapter.setData(resulPhotos.getPhotos());
    }
    });
    } catch (MalformedURLException e) {
    e.printStackTrace();
    } catch (IOException e) {
    e.printStackTrace();
    }*/

