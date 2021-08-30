package com.erdees.netgurushoppinglist.model.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.erdees.netgurushoppinglist.model.models.ShoppingList



class BusinessLogicRepository {

    private var presentedShoppingList : ShoppingList? = null
    private val presentedShoppingListLive =  MutableLiveData<ShoppingList?>()


    init {
        presentedShoppingListLive.value = presentedShoppingList
    }


    fun setShoppingListToPresent(shoppingList: ShoppingList){
        presentedShoppingList = shoppingList
        presentedShoppingListLive.value = presentedShoppingList
    }

    fun getShoppingListToPresent() = presentedShoppingListLive as LiveData<ShoppingList?>


    fun clearShoppingListToPresent() {
        presentedShoppingList = null
        presentedShoppingListLive.value = presentedShoppingList
    }

}