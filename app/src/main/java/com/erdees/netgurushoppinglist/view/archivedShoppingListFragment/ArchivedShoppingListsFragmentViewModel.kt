package com.erdees.netgurushoppinglist.view.archivedShoppingListFragment

import androidx.lifecycle.ViewModel
import com.erdees.netgurushoppinglist.model.models.ShoppingList
import com.erdees.netgurushoppinglist.model.repositories.ShoppingListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ArchivedShoppingListsFragmentViewModel @Inject constructor(
    shoppingListRepository: ShoppingListRepository,
) : ViewModel() {
    val getArchivedShoppingLists = shoppingListRepository.getArchivedShoppingLists
}