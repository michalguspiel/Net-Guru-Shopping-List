package com.erdees.netgurushoppinglist.model.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "shopping_list")
data class ShoppingList(
    @PrimaryKey(autoGenerate = true) val id : Long,
    val name : String,
    val isActive : Boolean,
    val creationDate : Date,
    val archivingDate: Date? = null
) {
}