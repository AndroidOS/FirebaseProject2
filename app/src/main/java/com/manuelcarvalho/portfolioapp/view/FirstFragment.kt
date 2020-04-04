package com.manuelcarvalho.portfolioapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.manuelcarvalho.portfolioapp.R
import com.manuelcarvalho.portfolioapp.viewmodel.AppViewModel


class FirstFragment : Fragment() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var viewModel: AppViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        viewModel = activity?.run {
            ViewModelProviders.of(this)[AppViewModel::class.java]
        } ?: throw Exception("Invalid Activity")

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
        observeViewModel()
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

    private fun observeViewModel() {

        viewModel.logout.observe(viewLifecycleOwner, Observer { logout ->
            logout?.let {
                if (logout == false) {
                    findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
                }
            }
        })
    }
}
