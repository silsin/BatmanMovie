package com.fakhari.batmanmovie.ui.moviedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fakhari.batmanmovie.App
import com.fakhari.batmanmovie.base.BaseViewModel
import com.fakhari.batmanmovie.base.UiState
import com.fakhari.batmanmovie.data.model.MovieModel
import com.fakhari.batmanmovie.data.model.ResultWrapper
import com.fakhari.batmanmovie.data.network.api.ApiService
import com.fakhari.batmanmovie.di.AppModule
import com.fakhari.batmanmovie.utility.SingleLiveEvent
import javax.inject.Inject
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException


class MovieDetailViewModel (private var api: ApiService):
    BaseViewModel() {

    fun fetchData(movieId: String) {
       requestMovieDetail(movieId)
    }
    var movieDetailError = SingleLiveEvent<Boolean>()
    var movieDetailLiveData = SingleLiveEvent<MovieModel?>()
    fun requestMovieDetail(movieId:String){
        uiStateObservable.postValue(UiState.Loading(true))
        disposable.add(
            api.getMovieDetail(movieId=movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    movieDetailLiveData.postValue(it)

                    uiStateObservable.postValue(UiState.Loading(false))
                }, { throwable ->
                    uiStateObservable.postValue(UiState.Loading(false))

                    val httpException = throwable as? HttpException
                    if (httpException != null) {
                      movieDetailError.postValue(true)
                    }
                })
        )
    }

}