package com.fakhari.batmanmovie.ui.movielist

import com.fakhari.batmanmovie.App
import com.fakhari.batmanmovie.base.BaseViewModel
import com.fakhari.batmanmovie.base.UiState
import com.fakhari.batmanmovie.data.model.MovieSearchModel
import com.fakhari.batmanmovie.data.network.api.ApiService
import com.fakhari.batmanmovie.utility.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException


class MovieListVieModel (private var api: ApiService) :
    BaseViewModel() {
    fun fetchData() {
       requestBatmanMovie()
    }
     var movieLiveData = SingleLiveEvent<MovieSearchModel?>()
     var movieErrorData = SingleLiveEvent<Boolean>()
    private fun requestBatmanMovie() {
        uiStateObservable.postValue(UiState.Loading(true))
        disposable.add(
            api.getBatmanMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    movieLiveData.postValue(it)
                    uiStateObservable.postValue(UiState.Loading(false))
                }, { throwable ->
                    uiStateObservable.postValue(UiState.Loading(false))

                    val httpException = throwable as? HttpException
                    if (httpException != null) {
                        movieErrorData.postValue(true)
                    }

                })
        )
    }



}