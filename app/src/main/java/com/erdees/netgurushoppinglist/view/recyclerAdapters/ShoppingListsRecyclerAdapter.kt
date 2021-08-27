package com.erdees.netgurushoppinglist.view.recyclerAdapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.erdees.netgurushoppinglist.model.ShoppingList

class ShoppingListsRecyclerAdapter(private val list: List<ShoppingList>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return list.size
    }

    companion object {
        const val TAG = "ShoppingListsRecyclerAdapter"
    }
}