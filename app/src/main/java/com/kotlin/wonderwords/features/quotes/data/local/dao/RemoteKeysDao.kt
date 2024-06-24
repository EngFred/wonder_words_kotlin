package com.kotlin.wonderwords.features.quotes.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.kotlin.wonderwords.features.quotes.data.modals.RemoteKey

@Dao
interface RemoteKeysDao {
    @Query("SELECT * FROM REMOTE_KEYS_TABLE WHERE id = :id")
    suspend fun getRemoteKeyById(id: Int) : RemoteKey

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRemoteKeys( remoteKeys: List<RemoteKey> )

    @Query("DELETE FROM REMOTE_KEYS_TABLE")
    suspend fun deleteRemoteKeys()

}