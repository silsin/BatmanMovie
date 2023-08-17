package com.fakhari.batmanmovie.data.repository

import com.fakhari.batmanmovie.data.model.MovieSearchModel
import com.fakhari.batmanmovie.data.model.ResultWrapper
import com.fakhari.batmanmovie.data.model.SearchMovieModel

interface MoviesListRepoInterface {

    /**
     * get all movies from network
     */
    suspend fun getAllFromNetwork(): ResultWrapper<MovieSearchModel>

    /**
     * insert movies to database
     */
    suspend fun insertToDatabase(movies: List<SearchMovieModel>)

    /**
     * get all data from database
     */
    suspend fun getMoviesFromNDatabase(): List<SearchMovieModel>

    /**
     * delete all database entity
     */
    suspend fun deleteAll()

}