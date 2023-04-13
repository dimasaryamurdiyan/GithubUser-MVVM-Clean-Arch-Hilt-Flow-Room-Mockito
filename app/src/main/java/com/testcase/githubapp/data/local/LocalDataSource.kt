package com.testcase.githubapp.data.local

import com.testcase.githubapp.data.local.dao.UserDao
import com.testcase.githubapp.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow
import java.security.PrivateKey
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val userDao: UserDao,
) {
    fun getUsers(): List<UserEntity> = userDao.getAllUsers()

    suspend fun insertAllUsers(users: List<UserEntity>) = userDao.insertAll(users)

    suspend fun deleteAllUsers(users: List<UserEntity>) = userDao.deleteAll(users)
}

