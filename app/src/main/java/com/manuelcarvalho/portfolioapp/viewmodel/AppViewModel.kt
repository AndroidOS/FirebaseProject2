package com.manuelcarvalho.portfolioapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.manuelcarvalho.portfolioapp.model.Part


private const val TAG = "AppViewModel"

class AppViewModel(application: Application) : BaseViewModel(application) {


    val db = Firebase.firestore
    val logout = MutableLiveData<Boolean>()
    val carts by lazy { MutableLiveData<List<Part>>() }

    fun refresh() {
        Log.w(TAG, "Refresh ")
        db.collection("carts")
            .whereEqualTo("computer", "VIC-20")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }

    }


}