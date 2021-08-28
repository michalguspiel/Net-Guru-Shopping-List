package com.erdees.netgurushoppinglist.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdees.netgurushoppinglist.model.ShoppingList
import com.erdees.netgurushoppinglist.model.repositories.ShoppingListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val shoppingListRepository: ShoppingListRepository) : ViewModel() {

    fun addShoppingList(shoppingList : ShoppingList){
       viewModelScope.launch(Dispatchers.IO) {
           shoppingListRepository.addShoppingList(shoppingList)
       }
    }

}