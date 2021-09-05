package com.erdees.netgurushoppinglist.view.recyclerAdapters

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.erdees.netgurushoppinglist.databinding.ArchivedShoppingListItemBinding
import com.erdees.netgurushoppinglist.model.models.ShoppingList
import com.erdees.netgurushoppinglist.Constants.ACTIVE_SHOPPING_LIST_TYPE
import com.erdees.netgurushoppinglist.Constants.ARCHIVED_SHOPPING_LIST_TYPE
import com.erdees.netgurushoppinglist.Constants.SHOPPING_LIST_KEY
import com.erdees.netgurushoppinglist.R
import com.erdees.netgurushoppinglist.Utils
import com.erdees.netgurushoppinglist.databinding.ActiveShoppingListsItemBinding
import com.erdees.netgurushoppinglist.view.singleShoppingListFragment.SingleShoppingListFragment
import com.erdees.netgurushoppinglist.view.activeShoppingListFragment.ActiveShoppingListsFragmentViewModel
import com.erdees.netgurushoppinglist.view.mainActivity.MainActivity
import java.lang.NullPointerException

class ShoppingListsRecyclerAdapter(
    private val activity: Activity,
    private val fm: FragmentManager,
    private val viewModel: ViewModel
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var list : MutableList<ShoppingList> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun updateShoppingList(updatedListOfShoppingLists: List<ShoppingList>) {
        list = updatedListOfShoppingLists.toMutableList()
        notifyDataSetChanged()
    }

    val singleShoppingListFragment = (activity as MainActivity).singleShoppingListFragment

    inner class ArchivedItemViewHolder(private val viewBinding: ArchivedShoppingListItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(position: Int) {
            viewBinding.shoppingListName.text = list[position].name
            viewBinding.shoppingListCreationDate.text = activity.getString(
                R.string.list_created, DateFormat.format(
                    "dd-MM-yy", list[position].creationDate
                )
            )
            if (list[position].archivingDate != null) viewBinding.shoppingListArchivingDate.text =
                activity.getString(
                    R.string.list_archived, DateFormat.format(
                        "dd-MM-yy", list[position].archivingDate
                    )
                )
            viewBinding.shoppingListItemLayout.setOnClickListener {
                singleShoppingListFragment.passShoppingListAsArgument(list[position])
                Utils.openFragment(singleShoppingListFragment, SingleShoppingListFragment.TAG, fm)
            }
        }
    }

    inner class ActiveItemViewHolder(private val viewBinding: ActiveShoppingListsItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(position: Int) {
            viewBinding.shoppingListName.text = list[position].name
            viewBinding.shoppingListCreationDate.text = activity.getString(
                R.string.list_created, DateFormat.format(
                    "dd-MM-yy", list[position].creationDate
                )
            )
            viewBinding.shoppingListItemLayout.setOnClickListener {
                singleShoppingListFragment.passShoppingListAsArgument(list[position])
                Utils.openFragment(singleShoppingListFragment, SingleShoppingListFragment.TAG, fm)
            }
            viewBinding.shoppingListItemLayout.createPopUpMenuOnLongClick(position, viewBinding)
        }
    }

    private fun SingleShoppingListFragment.passShoppingListAsArgument(shoppingList: ShoppingList){
        val arguments = Bundle()
        arguments.putParcelable(SHOPPING_LIST_KEY,shoppingList)
        this.arguments = arguments
        Log.i(TAG,this.arguments.toString())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ACTIVE_SHOPPING_LIST_TYPE -> ActiveItemViewHolder(
                ActiveShoppingListsItemBinding.inflate(
                    LayoutInflater.from(activity), parent, false
                )
            )
            ARCHIVED_SHOPPING_LIST_TYPE -> ArchivedItemViewHolder(
                ArchivedShoppingListItemBinding.inflate(
                    LayoutInflater.from(activity),
                    parent,
                    false
                )
            )
            else -> throw NullPointerException(activity.getString(R.string.wrong_type))
        }

    }

    override fun getItemViewType(position: Int): Int {
        return when (list[position].isActive) {
            true -> ACTIVE_SHOPPING_LIST_TYPE
            else -> ARCHIVED_SHOPPING_LIST_TYPE
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == ACTIVE_SHOPPING_LIST_TYPE) (holder as ShoppingListsRecyclerAdapter.ActiveItemViewHolder).bind(
            position
        )
        else if (holder.itemViewType == ARCHIVED_SHOPPING_LIST_TYPE) (holder as ShoppingListsRecyclerAdapter.ArchivedItemViewHolder).bind(
            position
        )
        else throw NullPointerException(activity.getString(R.string.wrong_type))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun View.createPopUpMenuOnLongClick(
        position: Int,
        viewBinding: ActiveShoppingListsItemBinding
    ) {
        val viewModel = (viewModel as ActiveShoppingListsFragmentViewModel)
        this.setOnLongClickListener {
            val popupMenu = PopupMenu(context, viewBinding.shoppingListPopUpMenuAnchor)
            popupMenu.menuInflater.inflate(
                R.menu.active_shopping_list_context_menu,
                popupMenu.menu
            )
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.deleteShoppingList -> {
                        viewModel.deleteShoppingList(list[position])
                        return@setOnMenuItemClickListener true
                    }
                    R.id.archiveShoppingList -> {
                        viewModel.archiveShoppingList(list[position])
                        return@setOnMenuItemClickListener true
                    }
                    else -> {
                        return@setOnMenuItemClickListener true
                    }
                }
            }
            popupMenu.show()
            true
        }
    }

    companion object {
        const val TAG = "ShopListsRVAdapter"
    }
}