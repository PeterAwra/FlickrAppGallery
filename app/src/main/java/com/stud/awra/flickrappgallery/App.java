package com.stud.awra.flickrappgallery;

import android.app.Application;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {
  private static ApiFlickrService apiFlickrService;

  public static ApiFlickrService getApiFlickrService() {
    if (apiFlickrService == null) {
      apiFlickrService = new Retrofit.Builder().baseUrl("https://api.flickr.com/")
          .addConverterFactory(GsonConverterFactory.create())
          .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
          .build().create(ApiFlickrService.class);
    }
    return apiFlickrService;
  }
}
