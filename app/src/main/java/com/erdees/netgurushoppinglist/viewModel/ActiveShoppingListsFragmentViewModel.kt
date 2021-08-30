package com.erdees.netgurushoppinglist.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdees.netgurushoppinglist.model.models.ShoppingList
import com.erdees.netgurushoppinglist.model.repositories.BusinessLogicRepository
import com.erdees.netgurushoppinglist.model.repositories.ShoppingListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ActiveShoppingListsFragmentViewModel @Inject constructor(
    private val shoppingListRepository: ShoppingListRepository,
    private val businessLogicRepository: BusinessLogicRepository
) : ViewModel() {

    val getActiveShoppingLists = shoppingListRepository.getActiveShoppingLists

    fun setShoppingListToPresent(shoppingListToPresent: ShoppingList) =
        businessLogicRepository.setShoppingListToPresent(shoppingListToPresent)

    fun deleteShoppingList(shoppingList: ShoppingList) {
        viewModelScope.launch(Dispatchers.IO) {
            shoppingListRepository.deleteShoppingList(shoppingList)
        }
    }

    fun addShoppingList(shoppingList: ShoppingList) {
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