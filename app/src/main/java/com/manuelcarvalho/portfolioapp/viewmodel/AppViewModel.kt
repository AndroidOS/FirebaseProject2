package com.manuelcarvalho.portfolioapp.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData


private const val TAG = "AppViewModel"

class AppViewModel(application: Application) : BaseViewModel(application) {


    val logout = MutableLiveData<Boolean>()

    fun refresh() {

    }


}