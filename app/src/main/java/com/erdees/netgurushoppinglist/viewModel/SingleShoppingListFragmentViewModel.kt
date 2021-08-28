package com.erdees.netgurushoppinglist.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.erdees.netgurushoppinglist.model.ShoppingList
import com.erdees.netgurushoppinglist.model.repositories.BusinessLogicRepository
import com.erdees.netgurushoppinglist.model.repositories.GroceryItemRepository
import com.erdees.netgurushoppinglist.model.repositories.ShoppingListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
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

}