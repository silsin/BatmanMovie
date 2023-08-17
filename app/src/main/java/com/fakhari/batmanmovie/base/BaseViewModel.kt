package com.fakhari.batmanmovie.base

import androidx.lifecycle.ViewModel
import com.fakhari.batmanmovie.utility.SingleLiveEvent


import io.reactivex.disposables.CompositeDisposable

//base of view model
abstract class BaseViewModel:ViewModel() {
    var disposable=CompositeDisposable()
    var uiStateObservable = SingleLiveEvent<UiState>()

     fun onClear(){
        disposable.clear()
    }
}