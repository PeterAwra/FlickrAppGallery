package com.stud.awra.flickrappgallery;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FlickrApi {
  public static final String API_KEY = "996aa5d376ac2ea15b93a2c9d4df589b";
  public static final String METHOD = "flickr.photos.getRecent";
  public static final String FORMAT = "json";
  public static final int NO_JSON_CALLBACK = 1;

  @GET("services/rest/")
  Call<ResultFotos> getData(@Query("method") String method,
      @Query("api_key") String api_key,
      @Query("format") String format,
      @Query("nojsoncallback") int nojsoncallback);
}
