package com.manuelcarvalho.portfolioapp.view

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.manuelcarvalho.portfolioapp.R
import com.manuelcarvalho.portfolioapp.model.Part
import com.manuelcarvalho.portfolioapp.viewmodel.AppViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.io.InputStream

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: AppViewModel
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        viewModel = ViewModelProviders.of(this)[AppViewModel::class.java]

        fab.setOnClickListener { view ->
            //bulkData()
            //testData()
            //readData()
            viewModel.refresh()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_logout -> {
                viewModel.logout.value = true
                return true
            }
            R.id.action_add -> {
                viewModel.add.value = true
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun testData() {
        // Create a new user with a first and last name
//        val user = hashMapOf(
//            "first" to "Ada",
//            "last" to "Lovelace",
//            "born" to 1815
//        )
        //val part = Part("Commodore", "VIC-20", "Gorf", "Good")

// Add a new document with a generated ID
//        Log.d(TAG, "testdata method")
//        db.collection("carts")
//            .add(part)
//            .addOnSuccessListener { documentReference ->
//                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
//            }
//            .addOnFailureListener { e ->
//                Log.w(TAG, "Error adding document", e)
//            }

    }

    private fun readData() {
        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }

    private fun bulkData() {
        var cartList: List<Part>? = null
        try {
            val inputStream: InputStream = assets.open("cart.txt")
            val text = inputStream.bufferedReader().use { it.readText() }

            val a = text.split("\n")

            val manufacturer = a[0].substring(startIndex = 0, endIndex = 12)

            cartList = splitString(a)

        } catch (e: Exception) {
            Log.d(TAG, e.toString())
        }

        Log.d(TAG, "testdata method")
        if (cartList != null) {
            for (c in cartList) {


                db.collection("carts")
                    .add(c)
                    .addOnSuccessListener { documentReference ->
                        Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Error adding document", e)
                    }
            }
        }
    }

    private fun splitString(stringArray: List<String>): List<Part> {
        val cartList = mutableListOf<Part>()
        for (t in stringArray) {
            val manufacturer = t.substring(startIndex = 0, endIndex = 12)
            val cartridge = t.substring(startIndex = 12, endIndex = 44)
            val partNum = t.substring(startIndex = 44, endIndex = 56)
            val memUse = t.substring(startIndex = 57, endIndex = 60)
            val gamePlay = t.substring(startIndex = 62, endIndex = 67)
            val scarcity = t.substring(startIndex = 68, endIndex = 72)
            val releaseYear = t.substring(startIndex = 73, endIndex = 77)
            cartList.add(
                Part(
                    manufacturer,
                    "VIC-20",
                    cartridge,
                    "Good",
                    partNum,
                    memUse,
                    gamePlay,
                    scarcity,
                    releaseYear
                )
            )
        }

        return cartList
    }
}
