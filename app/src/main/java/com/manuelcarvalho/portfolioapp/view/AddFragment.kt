package com.manuelcarvalho.portfolioapp.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.manuelcarvalho.portfolioapp.R
import com.manuelcarvalho.portfolioapp.model.Part
import com.manuelcarvalho.portfolioapp.viewmodel.AppViewModel
import kotlinx.android.synthetic.main.fragment_add.*

private const val TAG = "AddFragment"
class AddFragment : Fragment() {

    private lateinit var viewModel: AppViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = activity?.run {
            ViewModelProviders.of(this)[AppViewModel::class.java]
        } ?: throw Exception("Invalid Activity")

        viewModel.fabDisplay.value = false

        btn_add.setOnClickListener {

            val part = Part(
                ed_manufacturer.text.toString(),
                "Commodore VIC-20",
                ed_cartridge.text.toString(),
                ed_condition.text.toString(),
                ed_partNum.text.toString(),
                ed_romUse.text.toString(),
                ed_gamePlay.text.toString(),
                ed_rarity.text.toString(),
                ed_yearMade.text.toString()
            )

            Log.d(TAG, "$part")

        }
    }

    private fun checkForEmptyString(str: String): Boolean {
        return str == ""
    }

}
