package com.erdees.netgurushoppinglist.view.recyclerAdapters

import android.content.Context
import android.provider.SyncStateContract
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.erdees.netgurushoppinglist.Constants
import com.erdees.netgurushoppinglist.databinding.ActiveSingleShoppingListItemBinding
import com.erdees.netgurushoppinglist.databinding.ArchivedSingleShoppingListItemBinding
import com.erdees.netgurushoppinglist.model.GroceryItem
import com.erdees.netgurushoppinglist.model.ShoppingList
import java.lang.NullPointerException

class SingleShoppingListRecyclerAdapter(
    val list: List<GroceryItem>,
    val shoppingList: ShoppingList,
    val context: Context
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class ActiveListViewHolder(private val viewBinding: ActiveSingleShoppingListItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root){
            fun bind(position: Int) {
                viewBinding.itemName.text = list[position].name
            }

        }

    inner class ArchivedListViewHolder(private val viewBinding: ArchivedSingleShoppingListItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
            fun bind(position: Int) {
                viewBinding.itemName.text = list[position].name
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            Constants.ACTIVE_SHOPPING_LIST_TYPE -> {
                ActiveListViewHolder(
                    ActiveSingleShoppingListItemBinding.inflate(
                        LayoutInflater.from(
                            context
                        ), parent, false
                    )
                )
            }
            Constants.ARCHIVED_SHOPPING_LIST_TYPE -> {
                ArchivedListViewHolder(
                    ArchivedSingleShoppingListItemBinding.inflate(
                        LayoutInflater.from(context), parent, false
                    )
                )
            }
            else -> {
                throw NullPointerException("WRONG TYPE!")
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (shoppingList.isActive) {
            true -> Constants.ACTIVE_SHOPPING_LIST_TYPE
            else -> Constants.ARCHIVED_SHOPPING_LIST_TYPE
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            Constants.ACTIVE_SHOPPING_LIST_TYPE -> {
                (holder as ActiveListViewHolder).bind(position)
            }
            Constants.ARCHIVED_SHOPPING_LIST_TYPE -> {
                (holder as ArchivedListViewHolder).bind(position)
            }
            else -> throw NullPointerException("WRONG TYPE !!!")
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}