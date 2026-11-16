package com.microcareer.match.data.local.dao

import androidx.room.*
import com.microcareer.match.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE id = :userId")
    fun getUser(userId: String): Flow<UserEntity?>

    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserOnce(userId: String): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Update
    suspend fun updateUser(user: UserEntity)

    @Query("UPDATE users SET isSynced = :synced WHERE id = :userId")
    suspend fun updateSyncStatus(userId: String, synced: Boolean)

    @Query("SELECT * FROM users WHERE isSynced = 0")
    suspend fun getUnsyncedUsers(): List<UserEntity>

    @Query("DELETE FROM users WHERE id = :userId")
    suspend fun deleteUser(userId: String)
    @Query("DELETE FROM users")
    suspend fun clearAll()
}
