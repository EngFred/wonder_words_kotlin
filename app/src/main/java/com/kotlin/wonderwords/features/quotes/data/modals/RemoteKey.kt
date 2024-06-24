package com.kotlin.wonderwords.features.quotes.data.modals

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Remote_keys_table")
data class RemoteKey(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val prevPage: Int?,
    val nextPage: Int?
)
