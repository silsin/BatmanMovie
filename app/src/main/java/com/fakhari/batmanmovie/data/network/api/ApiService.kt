package com.fakhari.batmanmovie.data.network.api

import com.fakhari.batmanmovie.data.model.MovieModel
import com.fakhari.batmanmovie.data.model.MovieSearchModel
import com.fakhari.batmanmovie.utility.ConstValue.API_KEY
import com.fakhari.batmanmovie.utility.SingleLiveEvent
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/")
     fun getBatmanMovies(
        @Query("apikey") apiKey: String = API_KEY,
        @Query("s") name: String = "batman"
    ): Single<MovieSearchModel>

    @GET("/")
     fun getMovieDetail(
        @Query("apikey") apiKey: String = API_KEY,
        @Query("i") movieId: String
    ): Single<MovieModel>

}
