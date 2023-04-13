package com.testcase.githubapp.data.local.dao

import androidx.room.*
import com.testcase.githubapp.data.local.entity.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAllUsers() : List<UserEntity>

    @Query("SELECT * FROM user WHERE login = :username")
    fun getUser(username: String): UserEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<UserEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: UserEntity)

    @Delete
    suspend fun deleteAll(users: List<UserEntity>)
}