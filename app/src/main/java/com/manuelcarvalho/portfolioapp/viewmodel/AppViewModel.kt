package com.manuelcarvalho.portfolioapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.manuelcarvalho.portfolioapp.model.Part
import kotlinx.coroutines.launch


private const val TAG = "AppViewModel"

class AppViewModel(application: Application) : BaseViewModel(application) {


    val db = Firebase.firestore
    val logout = MutableLiveData<Boolean>()
    val add = MutableLiveData<Boolean>()
    val manufacturers = MutableLiveData<List<String>>()
    val carts by lazy { MutableLiveData<List<Part>>() }
    val fabDisplay = MutableLiveData<Boolean>()
    val listEmpty = MutableLiveData<Boolean>()
    val manuSelect = MutableLiveData<String>()

    fun refresh(choice: String) {
        // Log.w(TAG, "$choice")
        launch {
            db.collection("carts")
                .whereEqualTo("manufacturer", choice)
                .get()
                .addOnSuccessListener { documents ->
                    createList(documents)
                    if (documents.isEmpty) {
                        //listEmpty.value = false
//                        Log.d(TAG, "isEmpty true")
                    } else {
                        //listEmpty.value = true
//                        Log.d(TAG, "isEmpty false")
                    }

                    for (document in documents) {
                        Log.d(TAG, "${document.id} => ${document.data}")
                        Log.d(TAG, "${documents.size()}")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents: ", exception)
                }

        }
    }


    private fun createList(documents: QuerySnapshot) {
        launch {
            var cartList = mutableListOf<Part>()
            var manu = mutableListOf<String>()
            Log.d(TAG, "${documents}")
            for (document in documents) {
                Log.d(TAG, "${document}")
                manu.add(document.data["manufacturer"].toString())
                cartList.add(
                    Part(
                        document.data["manufacturer"].toString(),
                        document.data["computer"].toString(),
                        document.data["catridge"].toString(),
                        document.data["condition"].toString(),
                        document.data["partNum"].toString(),
                        document.data["romUse"].toString(),
                        document.data["gamePlay"].toString(),
                        document.data["rarity"].toString(),
                        document.data["yearMade"].toString()
                    )
                )
            }
            carts.value = cartList
            manufacturers.value = manu
        }
        //Log.d(TAG, "${cartList.get(0)}")

    }

    fun deleteItem(name: String) {
        launch {
            Log.d(TAG, "removeFirestoreItem")
            val db = Firebase.firestore
            db.collection("carts")
                .whereEqualTo("catridge", "Avenger  (Vic Avenger)")
                .get()
                .addOnSuccessListener { documents ->
                    val batch = db.batch()
                    for (d in documents) {
                        Log.d(TAG, "getting documents: ${d}")


                        db.collection("carts").whereEqualTo("catridge", name)
                            .get().result?.forEach {
                                batch.delete(it.reference)
                            }


                    }

                    batch.commit()
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "New Error getting documents: ", exception)
                }
        }
    }
}