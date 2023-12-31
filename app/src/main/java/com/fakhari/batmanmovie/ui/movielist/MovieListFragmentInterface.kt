package com.fakhari.batmanmovie.ui.movielist

/**
 * function of movie list
 */
interface MovieListFragmentInterface {

    /**
     * initialize the object
     */
    fun initObj()

    /**
     * get Data
     * At first will try network and then try the database to get data
     */
    fun getData()

    /**
     * observe liveData from viewModel
     */
    fun observeLiveData()


}