package com.manuelcarvalho.portfolioapp.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.manuelcarvalho.portfolioapp.R
import com.manuelcarvalho.portfolioapp.model.Part
import com.manuelcarvalho.portfolioapp.viewmodel.AppViewModel
import kotlinx.android.synthetic.main.fragment_second.*

private const val TAG = "SecondFragment"
class SecondFragment : Fragment() {

    private lateinit var viewModel: AppViewModel
    val db = Firebase.firestore

    private val listAdapter = ListAdapter(ArrayList<Part>())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = listAdapter
        }

        viewModel = activity?.run {
            ViewModelProviders.of(this)[AppViewModel::class.java]
        } ?: throw Exception("Invalid Activity")

        observeViewModel()

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

        viewModel.add.observe(viewLifecycleOwner, Observer { add ->
            add?.let {
                if (add == true) {
                    Log.d(TAG, "Viewmodel add")
                    viewModel.add.value = false
                    findNavController().navigate(R.id.action_SecondFragment_to_addFragment)
                }
            }
        })

        viewModel.carts.observe(viewLifecycleOwner, Observer { carts ->
            carts?.let {
                listAdapter.updatelist(carts)
            }
        })
    }
}
