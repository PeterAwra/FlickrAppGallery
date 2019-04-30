package com.stud.awra.flickrappgallery;

import android.app.Application;

public class App extends Application {
  public static FlickrApi getFlickrApi() {
    return NetworkService.getInstance().getApi();
  }
}
