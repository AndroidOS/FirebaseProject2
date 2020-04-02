package com.manuelcarvalho.portfolioapp.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import io.reactivex.disposables.CompositeDisposable


private const val TAG = "AppViewModel"

class AppViewModel(application: Application) : BaseViewModel(application) {

    private val disposable = CompositeDisposable()

    val readings = MutableLiveData<List<Double>>()
    val logout = MutableLiveData<Boolean>()

    fun refresh() {

    }


}