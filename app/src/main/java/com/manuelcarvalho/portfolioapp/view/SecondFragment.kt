package com.manuelcarvalho.portfolioapp.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.manuelcarvalho.portfolioapp.R
import com.manuelcarvalho.portfolioapp.viewmodel.AppViewModel

private const val TAG = "SecondFragment"
class SecondFragment : Fragment() {

    private lateinit var viewModel: AppViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = activity?.run {
            ViewModelProviders.of(this)[AppViewModel::class.java]
        } ?: throw Exception("Invalid Activity")

        observeViewModel()

        view.findViewById<Button>(R.id.button_second).setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }

    private fun observeViewModel() {

        viewModel.logout.observe(viewLifecycleOwner, Observer { logout ->
            logout?.let {
                if (logout == true) {
                    Log.d(TAG, "Viewmodel logout")
                    FirebaseAuth.getInstance()
                        .signOut()
                    findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
                }
            }
        })
    }
}
