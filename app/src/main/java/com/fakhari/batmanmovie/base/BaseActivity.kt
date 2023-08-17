package com.fakhari.batmanmovie.base

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

//base of activity
abstract class BaseActivity<VM: BaseViewModel>: AppCompatActivity() {
    var loadingView : View?=null
    abstract val vm:VM
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      // loadingView = findViewById(R.id.loading)
        observeUiState()
    }



    private fun observeUiState() {
        vm.uiStateObservable.observe(this) {
            when (it) {
                is UiState.Loading -> {
                    changeLoadingState(it.isLoading)
                }
                is UiState.Error -> {
                    changeLoadingState(false)
                    showToast(it.message)

                }
            }
        }
    }

    fun changeLoadingState(isLoading:Boolean){
        loadingView?.let {
            if(isLoading){
                it.visibility = View.VISIBLE
            }else{
                it.visibility = View.GONE
            }
        }
    }

    fun showToast(msg:String?){
        if(!msg.isNullOrBlank())
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        vm.onClear()
    }
}