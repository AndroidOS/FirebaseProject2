package com.manuelcarvalho.portfolioapp.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
    val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
    var choice: String? = ""

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


        //choice = sharedPref?.getString("choice", "HES")
        val preference = activity?.getSharedPreferences(
            resources.getString(R.string.app_name),
            Context.MODE_PRIVATE
        )
        choice = preference?.getString("choice", "HES")

        Log.d(TAG, "Choice value = $choice")

        activity?.title = "Items"
        recyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = listAdapter
        }

        viewModel = activity?.run {
            ViewModelProviders.of(this)[AppViewModel::class.java]
        } ?: throw Exception("Invalid Activity")



        choice?.let { viewModel.refresh(it) }

        observeViewModel()

        val touchHelperCallback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val cartName = listAdapter.removeItem(viewHolder)
                viewModel.deleteItem(cartName)
                Log.d(TAG, " cart Name $cartName")
            }

        }

        val itemTouchHelper = ItemTouchHelper(touchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerview)

    }


    override fun onResume() {
        super.onResume()
        viewModel.fabDisplay.value = true
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
                if (carts.isEmpty()) {
                    //fab.hide()
                } else {
                    //fab.show()
                }
            }
        })
    }
}
