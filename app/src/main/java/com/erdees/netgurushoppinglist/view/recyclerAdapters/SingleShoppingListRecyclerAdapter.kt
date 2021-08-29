package com.erdees.netgurushoppinglist.view.recyclerAdapters

import android.animation.Animator
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.erdees.netgurushoppinglist.Constants
import com.erdees.netgurushoppinglist.R
import com.erdees.netgurushoppinglist.databinding.ActiveSingleShoppingListItemBinding
import com.erdees.netgurushoppinglist.databinding.ArchivedSingleShoppingListItemBinding
import com.erdees.netgurushoppinglist.model.GroceryItem
import com.erdees.netgurushoppinglist.model.ShoppingList
import com.erdees.netgurushoppinglist.viewModel.SingleShoppingListFragmentViewModel
import java.lang.NullPointerException

class SingleShoppingListRecyclerAdapter(
    val list: List<GroceryItem>,
    val shoppingList: ShoppingList,
    val context: Context,
    val viewModel: SingleShoppingListFragmentViewModel
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class ActiveListViewHolder(private val viewBinding: ActiveSingleShoppingListItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(position: Int) {
            if (list[position].isInBasket) viewBinding.itemStatus.alpha =
                1.0f else viewBinding.itemStatus.alpha = 0.0f
            viewBinding.itemName.text = list[position].name
            viewBinding.itemLayout.createPopUpMenuOnLongClick(position, viewBinding)
            viewBinding.itemStatusBox.setOnClickListener {
                viewBinding.itemStatus.animateAndChangeItemStatus(position)
            }
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
                (holder as SingleShoppingListRecyclerAdapter.ActiveListViewHolder).bind(position)
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

    fun View.createPopUpMenuOnLongClick(
        position: Int,
        viewBinding: ActiveSingleShoppingListItemBinding
    ) {
        this.setOnLongClickListener {
            val popupMenu = PopupMenu(context, viewBinding.itemStatus)
            popupMenu.menuInflater.inflate(
                R.menu.single_grocery_item_context_menu,
                popupMenu.menu
            )
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.singleGroceryItemRemove -> {
                        viewModel.deleteGroceryItem(list[position])
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

    fun View.animateAndChangeItemStatus(position: Int) {
        val alpha: Float =
            if (list[position].isInBasket) 0.0f else 1.0f // If is in basket then disappearing animation otherwise appearing animation.
        this.animate()
            .alpha(alpha)
            .setListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {
                    viewModel.changeGroceryItemStatus(list[position])
                }

                override fun onAnimationCancel(animation: Animator?) {
                }

                override fun onAnimationRepeat(animation: Animator?) {
                }
            })
            .duration = 300L
    }


}