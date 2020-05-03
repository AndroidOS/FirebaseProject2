package com.manuelcarvalho.portfolioapp.utils

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

private const val TAG = "Utils"

fun removeFirestoreItem(name: String) {
    Log.d(TAG, "removeFirestoreItem")

    val db = Firebase.firestore.collection("carts")

    db
        .whereEqualTo("cartridge", name)
        .get()
        .addOnSuccessListener {

            Log.d(TAG, "")
            for (i in it) {

            }
        }
        .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }
}