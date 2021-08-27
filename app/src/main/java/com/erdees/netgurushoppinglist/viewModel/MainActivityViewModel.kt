package com.erdees.netgurushoppinglist.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.erdees.netgurushoppinglist.model.ShoppingList
import com.erdees.netgurushoppinglist.model.database.LocalDatabase
import com.erdees.netgurushoppinglist.model.repository.ShoppingListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    lateinit var shoppingListRepository: ShoppingListRepository

    init {
        val shoppingListDao = LocalDatabase.getDatabase(application).shoppingListDao()
        shoppingListRepository = ShoppingListRepository(shoppingListDao)
    }

    val getActiveShoppingLists = shoppingListRepository.getActiveShoppingLists

    fun addShoppingList(shoppingList : ShoppingList){
       viewModelScope.launch(Dispatchers.IO) {
           shoppingListRepository.addShoppingList(shoppingList)
       }
    }

}