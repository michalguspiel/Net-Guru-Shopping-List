package com.erdees.netgurushoppinglist.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "grocery_item")
data class GroceryItem(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name : String,
    val hostListId: Long
) {
}