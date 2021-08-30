package com.erdees.netgurushoppinglist.viewModel

import androidx.lifecycle.ViewModel
import com.erdees.netgurushoppinglist.model.models.ShoppingList
import com.erdees.netgurushoppinglist.model.repositories.BusinessLogicRepository
import com.erdees.netgurushoppinglist.model.repositories.ShoppingListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ArchivedShoppingListsFragmentViewModel @Inject constructor(
    shoppingListRepository: ShoppingListRepository,
    private val businessLogicRepository: BusinessLogicRepository
) : ViewModel() {

    val getArchivedShoppingLists = shoppingListRepository.getArchivedShoppingLists

    fun setShoppingListToPresent(shoppingListToPresent: ShoppingList) =
        businessLogicRepository.setShoppingListToPresent(shoppingListToPresent)

}