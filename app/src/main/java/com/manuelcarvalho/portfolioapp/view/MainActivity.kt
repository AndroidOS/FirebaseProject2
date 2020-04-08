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
            bulkData()
            //testData()
            //readData()
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
        try {
            val inputStream: InputStream = assets.open("cart.txt")
            val text = inputStream.bufferedReader().use { it.readText() }

            val a = text.split("\n")
            //Log.d(TAG, " ${text}")
            //val result = text.substring(startIndex = 0, endIndex = 10)
            Log.d(TAG, " ${a[10]}")
            //text = text.replace("JJ".toRegex(), "NN")
//            f.writeText(text)
        } catch (e: Exception) {
            Log.d(TAG, e.toString())
        }

        Log.d(TAG, "testdata method")
//        db.collection("carts")
//            .add(part)
//            .addOnSuccessListener { documentReference ->
//                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
//            }
//            .addOnFailureListener { e ->
//                Log.w(TAG, "Error adding document", e)
//            }
    }
}
