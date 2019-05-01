package com.stud.awra.flickrappgallery;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FlickrApi {
  String API_KEY = "996aa5d376ac2ea15b93a2c9d4df589b";
  String METHOD = "flickr.photos.getRecent";
  String FORMAT = "json";
  int NO_JSON_CALLBACK = 1;

  @GET("services/rest/")
  Call<ResultFotos> getData(@Query("method") String method,
      @Query("api_key") String api_key,
      @Query("format") String format,
      @Query("nojsoncallback") int nojsoncallback);

  @GET("services/rest/")
  Observable<ResultFotos> getQueryData(@Query("method") String method,
      @Query("api_key") String api_key,
      @Query("format") String format,
      @Query("nojsoncallback") int nojsoncallback,
      @Query("text") String query);
}
