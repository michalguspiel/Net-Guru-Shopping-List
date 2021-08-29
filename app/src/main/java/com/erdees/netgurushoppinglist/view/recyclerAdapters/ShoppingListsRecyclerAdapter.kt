package com.erdees.netgurushoppinglist.view.recyclerAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.erdees.netgurushoppinglist.databinding.ArchivedShoppingListItemBinding
import com.erdees.netgurushoppinglist.model.ShoppingList
import com.erdees.netgurushoppinglist.Constants.ACTIVE_SHOPPING_LIST_TYPE
import com.erdees.netgurushoppinglist.Constants.ARCHIVED_SHOPPING_LIST_TYPE
import com.erdees.netgurushoppinglist.R
import com.erdees.netgurushoppinglist.Utils
import com.erdees.netgurushoppinglist.databinding.ActiveShoppingListsItemBinding
import com.erdees.netgurushoppinglist.view.fragments.SingleShoppingListFragment
import com.erdees.netgurushoppinglist.viewModel.ActiveShoppingListsFragmentViewModel
import com.erdees.netgurushoppinglist.viewModel.ArchivedShoppingListsFragmentViewModel
import java.lang.NullPointerException

class ShoppingListsRecyclerAdapter(
    private val list: List<ShoppingList>,
    private val context: Context,
    private val fm: FragmentManager,
    private val viewModel: ViewModel
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val singleShoppingListFragment by lazy {
        SingleShoppingListFragment()
    }

    inner class ArchivedItemViewHolder(private val viewBinding: ArchivedShoppingListItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(position: Int) {
            viewBinding.itemName.text = list[position].shortDescription
            viewBinding.itemLayout.setOnClickListener {
                (viewModel as ArchivedShoppingListsFragmentViewModel).setShoppingListToPresent(list[position])
                Utils.openFragment(singleShoppingListFragment, SingleShoppingListFragment.TAG, fm)
                singleShoppingListFragment
            }
        }
    }

    inner class ActiveItemViewHolder(private val viewBinding: ActiveShoppingListsItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(position: Int) {
            val viewModel = (viewModel as ActiveShoppingListsFragmentViewModel)
            viewBinding.itemName.text = list[position].shortDescription
            viewBinding.itemLayout.setOnClickListener {
                viewModel.setShoppingListToPresent(list[position])
                Utils.openFragment(singleShoppingListFragment, SingleShoppingListFragment.TAG, fm)
            }
            viewBinding.itemLayout.createPopUpMenuOnLongClick(position, viewBinding)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ACTIVE_SHOPPING_LIST_TYPE -> ActiveItemViewHolder(
                ActiveShoppingListsItemBinding.inflate(
                    LayoutInflater.from(context), parent, false
                )
            )
            ARCHIVED_SHOPPING_LIST_TYPE -> ArchivedItemViewHolder(
                ArchivedShoppingListItemBinding.inflate(
                    LayoutInflater.from(context),
                    parent,
                    false
                )
            )
            else -> throw NullPointerException("ERROR, WRONG VIEW TYPE!")
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
        else throw NullPointerException("WRONG TYPE !!!")
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun View.createPopUpMenuOnLongClick(position: Int, viewBinding : ActiveShoppingListsItemBinding) {
        val viewModel = (viewModel as ActiveShoppingListsFragmentViewModel)
        this.setOnLongClickListener {
            val popupMenu = PopupMenu(context, viewBinding.itemLayout)
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
        const val TAG = "ShoppingListsRecyclerAdapter"
    }
}