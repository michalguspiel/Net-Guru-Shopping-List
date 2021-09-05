package com.erdees.netgurushoppinglist.view.singleShoppingListFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdees.netgurushoppinglist.model.models.GroceryItem
import com.erdees.netgurushoppinglist.model.repositories.GroceryItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SingleShoppingListFragmentViewModel @Inject constructor(
    private val groceryItemRepository: GroceryItemRepository,
) : ViewModel() {

    fun addGroceryItem(name: String, quantity: String, hostListId: Long) {
        val groceryItem = GroceryItem(
            0,
            name,
            quantity,
            hostListId,
            false
        )
        addGroceryItem(groceryItem)

    }

    private fun addGroceryItem(groceryItem: GroceryItem) {
        viewModelScope.launch(Dispatchers.IO) {
            groceryItemRepository.insertGroceryItem(groceryItem)
        }
    }

    fun getGroceriesToPresent(id: Long) = groceryItemRepository.getGroceriesForShoppingList(id)


    fun deleteGroceryItem(groceryItem: GroceryItem) {
        viewModelScope.launch(Dispatchers.IO) {
            groceryItemRepository.deleteGroceryItem(groceryItem)
        }
    }

    private fun editGroceryItem(groceryItem: GroceryItem) {
        viewModelScope.launch(Dispatchers.IO) {
            groceryItemRepository.editGroceryItem(groceryItem)
        }
    }

    fun changeGroceryItemStatus(groceryItem: GroceryItem): Boolean {
        val result: Boolean
        val updatedItem: GroceryItem
        if (groceryItem.isInBasket) {
            updatedItem = GroceryItem(
                groceryItem.id,
                groceryItem.name,
                groceryItem.quantity,
                groceryItem.hostListId,
                false
            )
            result = false
        } else {
            updatedItem = GroceryItem(
                groceryItem.id,
                groceryItem.name,
                groceryItem.quantity,
                groceryItem.hostListId,
                true
            )
            result = true
        }
        editGroceryItem(updatedItem)
        return result
    }

}