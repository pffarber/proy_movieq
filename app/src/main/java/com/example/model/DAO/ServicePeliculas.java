package com.example.model.DAO;

import com.example.model.pojo.PeliculaContainer;
import com.example.model.pojo.TrailerContainer;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

interface ServicePeliculas {

        @GET("3/movie/{category}")
        Call<PeliculaContainer> getPeliculasPaginables(
                @Path("category") String category,
                @Query("api_key") String apiKey,
                @Query("language") String language,
                @Query("page") int page
        );


        //example
        //https://api.themoviedb.org/3/movie/{movie_id}/videos?api_key=<<api_key>>&language=en-US
        @GET("3/movie/{movie_id}/videos")
        Call<TrailerContainer> getTrailers(
                @Path("movie_id") Integer movie_id,
                @Query("api_key") String apiKey,
                @Query("language") String language
        );


        //example
       // https://api.themoviedb.org/3/search/movie?api_key=61a79d269cb6589d31b134b7d4597e55&query=Increibl&page=1
        @GET("3/search/movie")
        Call<PeliculaContainer> getSearchMovie(
                @Query("api_key") String apiKey,
                @Query("language") String language,
                @Query("query") String searchText
        );

}