package com.manuelcarvalho.portfolioapp.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.manuelcarvalho.portfolioapp.R
import com.manuelcarvalho.portfolioapp.model.Part
import kotlinx.android.synthetic.main.list_view.view.*


private const val TAG = "ListAdapter"
class ListAdapter(val cartList: ArrayList<Part>) :
    RecyclerView.Adapter<ListAdapter.CartViewHolder>() {

    fun updatelist(cartList1: List<Part>) {
        cartList.clear()
        cartList.addAll(cartList1)
        notifyDataSetChanged()
    }

    fun removeItem(viewHolder: RecyclerView.ViewHolder): String {
        Log.d(TAG, "Adapter remove item ${viewHolder.adapterPosition}")


        //removeFirestoreItem(cartList[viewHolder.adapterPosition].catridge)

        val removeCart = cartList[viewHolder.adapterPosition].catridge
        cartList.removeAt(viewHolder.adapterPosition)
        notifyDataSetChanged()
        return removeCart

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(
            R.layout.list_view
            , parent, false
        )
        return CartViewHolder(view)
    }

    override fun getItemCount() = cartList.size

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.view.tv_cart.text = cartList[position].catridge
        holder.view.tv_manufacturer.text = cartList[position].manufacturer
        holder.view.tv_romUse.text = cartList[position].romUse


        holder.view.setOnClickListener {
            val cart = cartList[position].catridge
            Navigation.findNavController(it)
                .navigate(SecondFragmentDirections.actionSecondFragmentToWebFragment(cart))
        }

    }

    //.navigate(FirstFragmentDirections.actionFirstFragmentToSecondFragment())
    class CartViewHolder(var view: View) : RecyclerView.ViewHolder(view)
}