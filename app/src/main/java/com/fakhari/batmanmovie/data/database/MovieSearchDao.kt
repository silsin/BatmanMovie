package com.fakhari.batmanmovie.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fakhari.batmanmovie.data.model.SearchMovieModel

@Dao
interface MovieSearchDao {

    /**
     * insert all models
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
      fun insertAll(moviesList: List<SearchMovieModel>)

    /**
     * get all data
     */
    @Query("select * from SearchMovieModel")
      fun getAll():List<SearchMovieModel>

    /**
     * delete all data
     */
    @Query("delete from SearchMovieModel")
      fun deleteAll()

}