package com.erdees.netgurushoppinglist.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdees.netgurushoppinglist.model.models.GroceryItem
import com.erdees.netgurushoppinglist.model.repositories.BusinessLogicRepository
import com.erdees.netgurushoppinglist.model.repositories.GroceryItemRepository
import com.erdees.netgurushoppinglist.model.repositories.ShoppingListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SingleShoppingListFragmentViewModel @Inject constructor(
    private val groceryItemRepository: GroceryItemRepository,
    businessLogicRepository: BusinessLogicRepository
) : ViewModel() {

    val shoppingListToPresent = businessLogicRepository.getShoppingListToPresent()

    fun addGroceryItem(groceryItem: GroceryItem) {
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

    fun changeGroceryItemStatus(groceryItem: GroceryItem) : Boolean{
        val result : Boolean
        val updatedItem : GroceryItem
         if(groceryItem.isInBasket) {
           updatedItem = GroceryItem(groceryItem.id,groceryItem.name,groceryItem.quantity,groceryItem.hostListId,false)
             result = false
        }
        else {
            updatedItem = GroceryItem(groceryItem.id,groceryItem.name,groceryItem.quantity,groceryItem.hostListId,true)
             result = true
         }
        editGroceryItem(updatedItem)
        return result
    }

}