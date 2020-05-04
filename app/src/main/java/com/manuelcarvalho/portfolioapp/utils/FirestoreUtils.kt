package com.manuelcarvalho.portfolioapp.utils

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

private const val TAG = "Utils"

fun removeFirestoreItem(name: String) {
    Log.d(TAG, "removeFirestoreItem")
    val db = Firebase.firestore
    db.collection("carts")
        .whereEqualTo("catridge", "Avenger  (Vic Avenger)")
        .get()
        .addOnSuccessListener { documents ->
            val batch = db.batch()
            for (d in documents) {
                Log.d(TAG, "getting documents: ${d}")


                db.collection("carts").whereEqualTo("catridge", name).get().result?.forEach {
                    batch.delete(it.reference)
                }


            }

            batch.commit()
        }
        .addOnFailureListener { exception ->
            Log.w(TAG, "New Error getting documents: ", exception)
        }

}



