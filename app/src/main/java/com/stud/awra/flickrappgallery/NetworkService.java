package com.stud.awra.flickrappgallery;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {
  private static NetworkService instance;
  private final Retrofit retrofit;
  private final String baseUrl = "https://api.flickr.com/";

  public NetworkService() {
    retrofit = new Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
  }

  public static NetworkService getInstance() {
    if (instance == null) {
      instance = new NetworkService();
    }
    return instance;
  }
  public FlickrApi getApi(){
    return retrofit.create(FlickrApi.class);
  }
}
