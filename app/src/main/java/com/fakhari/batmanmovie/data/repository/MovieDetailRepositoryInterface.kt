package com.fakhari.batmanmovie.data.repository

import com.fakhari.batmanmovie.data.model.MovieModel
import com.fakhari.batmanmovie.data.model.ResultWrapper

/**
 * function if movie detail fragment
 */
interface MovieDetailRepositoryInterface{

    /**
     * get detail of movie
     */
    suspend fun getMovieDetail(movieId:String): ResultWrapper<MovieModel>
}