package com.testcase.githubapp.domain.repository

import com.testcase.githubapp.data.Resource
import com.testcase.githubapp.data.remote.response.GetSearchUserResponse
import com.testcase.githubapp.data.remote.response.GetUserReposResponse
import com.testcase.githubapp.data.remote.response.GetUserResponse
import com.testcase.githubapp.domain.model.Users
import kotlinx.coroutines.flow.Flow

interface IUserRepository {
    suspend fun getUsers(forceUpdate: Boolean): Flow<Resource<GetUserResponse>>
    suspend fun getUser(username: String): Flow<Resource<GetUserResponse.User>>
    suspend fun getUserFollowers(username: String): Flow<Resource<GetUserResponse>>
    suspend fun getUserFollowing(username: String): Flow<Resource<GetUserResponse>>
    suspend fun getUserRepos(username: String, perPage: Int): Flow<Resource<GetUserReposResponse>>
    suspend fun getSearchUsers(q: String, perPage: Int): Flow<Resource<GetUserResponse>>
}