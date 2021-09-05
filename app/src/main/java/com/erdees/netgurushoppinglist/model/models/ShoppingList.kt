package com.erdees.netgurushoppinglist.model.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "shopping_list")
data class ShoppingList(
    @PrimaryKey(autoGenerate = true) val id : Long,
    val name : String,
    val isActive : Boolean,
    val creationDate : Date,
    val archivingDate: Date? = null
) : Parcelable {
}