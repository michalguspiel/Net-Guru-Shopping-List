package com.erdees.netgurushoppinglist.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.erdees.netgurushoppinglist.model.database.LocalDatabase
import com.erdees.netgurushoppinglist.model.repository.ShoppingListRepository

class ActiveShoppingListsFragmentViewModel(application: Application) : AndroidViewModel(application) {

    var shoppingListRepository: ShoppingListRepository

    init {
        val shoppingListDao = LocalDatabase.getDatabase(application).shoppingListDao()
        shoppingListRepository = ShoppingListRepository(shoppingListDao)
    }

    val getActiveShoppingLists = shoppingListRepository.getActiveShoppingLists

}