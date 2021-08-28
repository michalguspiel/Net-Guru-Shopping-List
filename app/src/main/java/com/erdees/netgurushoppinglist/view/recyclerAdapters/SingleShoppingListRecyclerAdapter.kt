package com.erdees.netgurushoppinglist.view.recyclerAdapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.erdees.netgurushoppinglist.databinding.ActiveSingleShoppingListItemBinding
import com.erdees.netgurushoppinglist.databinding.ArchivedSingleShoppingListItemBinding

class SingleShoppingListRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class ActiveListViewHolder(private val viewBinding : ActiveSingleShoppingListItemBinding) : RecyclerView.ViewHolder(viewBinding.root)

    inner class ArchivedListViewHolder(private val viewBinding: ArchivedSingleShoppingListItemBinding) : RecyclerView.ViewHolder(viewBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}