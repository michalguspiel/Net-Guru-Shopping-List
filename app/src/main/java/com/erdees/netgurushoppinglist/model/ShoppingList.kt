package com.erdees.netgurushoppinglist.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.erdees.netgurushoppinglist.model.database.Converter
import java.util.*

@Entity(tableName = "shopping_list")
data class ShoppingList(
    @PrimaryKey(autoGenerate = true) val id : Long,
    val shortDescription : String,
    val isActive : Boolean,
    val timeStamp : Date
) {
}