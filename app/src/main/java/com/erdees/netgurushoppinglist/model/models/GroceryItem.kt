package com.erdees.netgurushoppinglist.model.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "grocery_item")
data class GroceryItem(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name : String,
    val quantity : String?,
    val hostListId: Long,
    val isInBasket : Boolean
) {
}