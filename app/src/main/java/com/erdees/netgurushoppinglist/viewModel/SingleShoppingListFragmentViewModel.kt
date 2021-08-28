package com.erdees.netgurushoppinglist.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.erdees.netgurushoppinglist.model.ShoppingList
import com.erdees.netgurushoppinglist.model.repositories.BusinessLogicRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SingleShoppingListFragmentViewModel @Inject constructor( businessLogicRepository: BusinessLogicRepository) : ViewModel() {

    val shoppingListToPresent = businessLogicRepository.getShoppingListToPresent()

}