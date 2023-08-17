package com.fakhari.batmanmovie.base

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController

//base of fragment
abstract class BaseFragment<VM : BaseViewModel> : Fragment() {
    abstract val vm: VM
    var loadingView: View? = null
    lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeUiState()
        initNavController()
    }

    private fun observeUiState() {
        vm.uiStateObservable.observe(this.viewLifecycleOwner) {
            when (it) {
                is UiState.Loading -> {
                    changeLoadingState(it.isLoading)
                }
                is UiState.Error -> {
                    changeLoadingState(false)
                    showError(it.message, it.type)
                }
            }
        }
    }

    open fun changeLoadingState(isLoading: Boolean) {
        loadingView?.let {
            if (isLoading) {
                it.visibility = View.VISIBLE
            } else {
                it.visibility = View.GONE
            }
        }
    }

    open fun showError(msg: String?, type: String) {
        if (!msg.isNullOrBlank())
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        vm.onClear()
    }

    fun locationPermissionGranted(): Boolean {

        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

    }

    private fun initNavController() {
        navController = findNavController()
    }


}