package com.erdees.netgurushoppinglist.view.recyclerAdapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.erdees.netgurushoppinglist.databinding.ArchivedShoppingListItemBinding
import com.erdees.netgurushoppinglist.databinding.ShoppingListsItemBinding
import com.erdees.netgurushoppinglist.model.ShoppingList
import com.erdees.netgurushoppinglist.Constants.ACTIVE_SHOPPING_LIST_TYPE
import com.erdees.netgurushoppinglist.Constants.ARCHIVED_SHOPPING_LIST_TYPE
import java.lang.NullPointerException

class ShoppingListsRecyclerAdapter(private val list: List<ShoppingList>, private val activity: Activity) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    inner class ArchivedItemViewHolder(private val viewBinding: ArchivedShoppingListItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

            fun bind(position: Int){
                viewBinding.itemName.text = list[position].shortDescription
            }
        }

     inner class ActiveItemViewHolder(private val viewBinding: ShoppingListsItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root){

         fun bind(position: Int){
             viewBinding.itemName.text = list[position].shortDescription
         }
     }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            ACTIVE_SHOPPING_LIST_TYPE -> ActiveItemViewHolder(ShoppingListsItemBinding.inflate(LayoutInflater.from(activity)))
                ARCHIVED_SHOPPING_LIST_TYPE -> ArchivedItemViewHolder(
                    ArchivedShoppingListItemBinding.inflate(LayoutInflater.from(activity)))
            else -> throw NullPointerException("ERROR, WRONG VIEW TYPE!")
        }

    }

    override fun getItemViewType(position: Int): Int {
        return when(list[position].isActive) {
            true -> ACTIVE_SHOPPING_LIST_TYPE
            else -> ARCHIVED_SHOPPING_LIST_TYPE
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder.itemViewType == ACTIVE_SHOPPING_LIST_TYPE) (holder as ShoppingListsRecyclerAdapter.ActiveItemViewHolder).bind(position)
        else if (holder.itemViewType == ARCHIVED_SHOPPING_LIST_TYPE) (holder as ShoppingListsRecyclerAdapter.ArchivedItemViewHolder).bind(position)
        else throw NullPointerException("WRONG TYPE !!!")
    }

    override fun getItemCount(): Int {
        return list.size
    }

    companion object {
        const val TAG = "ShoppingListsRecyclerAdapter"
    }
}