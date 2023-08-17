package com.fakhari.batmanmovie.base

// ui state of all app
sealed class UiState(){
    data class Loading(var isLoading:Boolean): UiState()
    data class Error(var message:String?,var type:String): UiState()
}