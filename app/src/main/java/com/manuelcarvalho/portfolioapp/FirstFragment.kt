package com.manuelcarvalho.portfolioapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth


class FirstFragment : Fragment() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAuth = FirebaseAuth.getInstance()

        if (mAuth.currentUser != null) {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        } else {
            Toast.makeText(
                activity, "User not signed in",
                Toast.LENGTH_SHORT
            ).show()
            loginUser()
            //startActivity(Intent(this, LoginActivity::class.java))
        }
//        view.findViewById<Button>(R.id.button_first).setOnClickListener {
//            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
//        }
    }

    private fun loginUser() {
        mAuth.signInWithEmailAndPassword("joe@gmail.com", "Pass123#")
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)

                } else {
                    Toast.makeText(
                        activity, "Authentication failure",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}
