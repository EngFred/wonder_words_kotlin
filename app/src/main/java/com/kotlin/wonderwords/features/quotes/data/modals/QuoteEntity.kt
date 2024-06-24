package com.kotlin.wonderwords.features.quotes.data.modals

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quotes_table")
data class QuoteEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val author: String?,
    val body: String?,
    val favorite: Boolean?
)
