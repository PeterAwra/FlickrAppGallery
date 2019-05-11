package com.stud.awra.flickrappgallery;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiFlickrService {
  String API_KEY = "26e83e1921b2d40964324325ff109858";
  String METHOD = "flickr.photos.getRecent";
  String FORMAT = "json";

  @GET(value = "services/rest/")
  Observable<ResultPhotos> getRecent(
      @Query(value = "method") String method,
      @Query(value = "api_key") String apiKey,
      @Query(value = "format") String format,
      @Query(value = "nojsoncallback") int nojson
      );

  @GET("services/rest/")
  Observable<ResultPhotos> getQueryData(
      @Query("method") String method,
      @Query("api_key") String api_key,
      @Query("format") String format,
      @Query("nojsoncallback") int nojsoncallback,
      @Query("text") String query);
}
