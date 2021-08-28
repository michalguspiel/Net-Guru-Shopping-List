package com.erdees.netgurushoppinglist.view.recyclerAdapters

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.erdees.netgurushoppinglist.databinding.ArchivedShoppingListItemBinding
import com.erdees.netgurushoppinglist.model.ShoppingList
import com.erdees.netgurushoppinglist.Constants.ACTIVE_SHOPPING_LIST_TYPE
import com.erdees.netgurushoppinglist.Constants.ARCHIVED_SHOPPING_LIST_TYPE
import com.erdees.netgurushoppinglist.Utils
import com.erdees.netgurushoppinglist.databinding.ActiveShoppingListsItemBinding
import com.erdees.netgurushoppinglist.view.fragments.SingleShoppingListFragment
import com.erdees.netgurushoppinglist.viewModel.ActiveShoppingListsFragmentViewModel
import com.erdees.netgurushoppinglist.viewModel.ArchivedShoppingListsFragmentViewModel
import java.lang.NullPointerException

class ShoppingListsRecyclerAdapter(private val list: List<ShoppingList>, private val activity: Activity, private val fm :FragmentManager,private val viewModel : ViewModel) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

  val singleShoppingListFragment by lazy {
      SingleShoppingListFragment()
  }

    inner class ArchivedItemViewHolder(private val viewBinding: ArchivedShoppingListItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

            fun bind(position: Int){
                viewBinding.itemName.text = list[position].shortDescription
                viewBinding.itemLayout.setOnClickListener {
                    (viewModel as ArchivedShoppingListsFragmentViewModel).setShoppingListToPresent(list[position])
                    Utils.openFragment(singleShoppingListFragment,SingleShoppingListFragment.TAG,fm)
                    singleShoppingListFragment
                }
            }
        }

     inner class ActiveItemViewHolder(private val viewBinding: ActiveShoppingListsItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root){

         fun bind(position: Int){
             viewBinding.itemName.text = list[position].shortDescription
             viewBinding.itemLayout.setOnClickListener {
                 (viewModel as ActiveShoppingListsFragmentViewModel).setShoppingListToPresent(list[position])
                 Utils.openFragment(singleShoppingListFragment, SingleShoppingListFragment.TAG, fm)
             }
         }
     }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            ACTIVE_SHOPPING_LIST_TYPE -> ActiveItemViewHolder(ActiveShoppingListsItemBinding.inflate(
                LayoutInflater.from(activity),parent,false))
                ARCHIVED_SHOPPING_LIST_TYPE -> ArchivedItemViewHolder(
                    ArchivedShoppingListItemBinding.inflate(LayoutInflater.from(activity),parent,false))
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