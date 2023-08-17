package com.fakhari.batmanmovie.data.network.disposable

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class DisposableManager {

    companion object {
        private var compositeDisposable: CompositeDisposable? = null

        fun add(disposable: Disposable) {
            getCompositeDisposable().add(disposable)
        }

        fun dispose() {
            getCompositeDisposable().clear()
        }

        private fun getCompositeDisposable(): CompositeDisposable {
            when {
                compositeDisposable == null || compositeDisposable!!.isDisposed -> compositeDisposable = CompositeDisposable()
            }
            return compositeDisposable as CompositeDisposable
        }
    }
}