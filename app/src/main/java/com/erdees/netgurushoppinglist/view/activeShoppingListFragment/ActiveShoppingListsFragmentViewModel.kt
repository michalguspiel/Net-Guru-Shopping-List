package com.erdees.netgurushoppinglist.view.activeShoppingListFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdees.netgurushoppinglist.model.models.ShoppingList
import com.erdees.netgurushoppinglist.model.repositories.ShoppingListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ActiveShoppingListsFragmentViewModel @Inject constructor(
    private val shoppingListRepository: ShoppingListRepository,
) : ViewModel() {

    val getActiveShoppingLists = shoppingListRepository.getActiveShoppingLists

    fun deleteShoppingList(shoppingList: ShoppingList) {
        viewModelScope.launch(Dispatchers.IO) {
            shoppingListRepository.deleteShoppingList(shoppingList)
        }
    }

    fun addShoppingList(listName : String){
        val shoppingListName : String = if(listName.isBlank()) "Basic shopping list" else listName
        val newShoppingList = ShoppingList(
            0, shoppingListName, true,
            Calendar.getInstance().time
        )
        addShoppingList(newShoppingList)
    }

    private fun addShoppingList(shoppingList: ShoppingList) {
        viewModelScope.launch(Dispatchers.IO) {
            shoppingListRepository.addShoppingList(shoppingList)
        }
    }

    private fun updateShoppingList(shoppingList: ShoppingList){
        viewModelScope.launch(Dispatchers.IO) {
            shoppingListRepository.updateShoppingList(shoppingList)
        }
    }

    fun archiveShoppingList(shoppingList: ShoppingList){
        val archivedShoppingList = ShoppingList(shoppingList.id,shoppingList.name,false,shoppingList.creationDate,Calendar.getInstance().time
        )
        updateShoppingList(archivedShoppingList)
    }
}