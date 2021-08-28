package com.erdees.netgurushoppinglist.viewModel

import androidx.lifecycle.ViewModel
import com.erdees.netgurushoppinglist.model.ShoppingList
import com.erdees.netgurushoppinglist.model.repositories.BusinessLogicRepository
import com.erdees.netgurushoppinglist.model.repositories.ShoppingListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ActiveShoppingListsFragmentViewModel @Inject constructor(
    shoppingListRepository: ShoppingListRepository,
    private val businessLogicRepository: BusinessLogicRepository
) : ViewModel() {

    val getActiveShoppingLists = shoppingListRepository.getActiveShoppingLists

    fun setShoppingListToPresent(shoppingListToPresent: ShoppingList) =
        businessLogicRepository.setShoppingListToPresent(shoppingListToPresent)

}