package com.stud.awra.flickrappgallery;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  public static final String KEY_APP = "26e83e1921b2d40964324325ff109858";
  public static final String SECRET = "88c03d77fb5da2d4";
  private TextView tv;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    tv = findViewById(R.id.tv);
    new Thread(new Runnable() {

      @Override public void run() {
        URL url = null;
        try {
          url = new URL("https://www.flickr.com/services/rest/?method=flickr.photos.getRecent&" +
              "api_key=" + KEY_APP + "&" +
              "format=json&" +
              "nojsoncallback=1");
          HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
          InputStream inputStream = httpURLConnection.getInputStream();
          int n = 0;
          char[] buffer = new char[1024 * 4];
          InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8");
          StringWriter writer = new StringWriter();
          while (-1 != (n = reader.read(buffer))) writer.write(buffer, 0, n);
          final String json = writer.toString();
          Gson gson = new GsonBuilder().create();
          ResultFotos resultFotos = gson.fromJson(json, ResultFotos.class);
          final StringBuilder s = new StringBuilder();
          List<Photo> photo = resultFotos.getPhotos().getPhoto();
          for (int i = 0; i < 10; i++) {
            s.append(
                (photo.get(i).getTitle().equals("")) ? "0" : photo.get(i).getTitle())
                .append("\n\n");
          }
          tv.post(new Runnable() {
            @Override public void run() {
              tv.setText(s.toString());
            }
          });
        } catch (MalformedURLException e) {
          e.printStackTrace();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }).start();
  }
}
