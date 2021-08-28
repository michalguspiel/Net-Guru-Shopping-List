package com.erdees.netgurushoppinglist.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdees.netgurushoppinglist.model.GroceryItem
import com.erdees.netgurushoppinglist.model.ShoppingList
import com.erdees.netgurushoppinglist.model.repositories.BusinessLogicRepository
import com.erdees.netgurushoppinglist.model.repositories.GroceryItemRepository
import com.erdees.netgurushoppinglist.model.repositories.ShoppingListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SingleShoppingListFragmentViewModel @Inject constructor(
    private val shoppingListRepository: ShoppingListRepository,
    private val groceryItemRepository: GroceryItemRepository,
    private val businessLogicRepository: BusinessLogicRepository
) : ViewModel() {

    val shoppingListToPresent = businessLogicRepository.getShoppingListToPresent()

    fun groceriesToPresent(id: Long) = groceryItemRepository.getGroceriesForShoppingList(id)

    fun clearShoppingListToPresent() = businessLogicRepository.clearShoppingListToPresent()

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

    fun changeGroceryItemStatus(groceryItem: GroceryItem) {
        val updatedItem : GroceryItem = if(groceryItem.isInBasket) {
            GroceryItem(groceryItem.id,groceryItem.name,groceryItem.hostListId,false)
        }
        else GroceryItem(groceryItem.id,groceryItem.name,groceryItem.hostListId,true)
        editGroceryItem(updatedItem)
    }

}