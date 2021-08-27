package com.erdees.netgurushoppinglist.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.erdees.netgurushoppinglist.model.database.LocalDatabase
import com.erdees.netgurushoppinglist.model.repository.ShoppingListRepository

class ArchivedShoppingListsFragmentViewModel(application: Application) : AndroidViewModel(application) {

    var shoppingListRepository: ShoppingListRepository

    init {
        val shoppingListDao = LocalDatabase.getDatabase(application).shoppingListDao()
        shoppingListRepository = ShoppingListRepository(shoppingListDao)
    }

    val getArchivedShoppingLists = shoppingListRepository.getArchivedShoppingLists

}