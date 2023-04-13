package com.testcase.githubapp.domain.usecase

import com.testcase.githubapp.data.Resource
import com.testcase.githubapp.data.remote.response.GetUserReposResponse
import com.testcase.githubapp.data.remote.response.GetUserResponse
import kotlinx.coroutines.flow.Flow

interface UserUseCase {
    suspend fun getUsers(forceUpdate: Boolean = false): Flow<Resource<GetUserResponse>>
    suspend fun getUser(username: String): Flow<Resource<GetUserResponse.User>>
    suspend fun getUserFollowers(username: String): Flow<Resource<GetUserResponse>>
    suspend fun getUserFollowing(username: String): Flow<Resource<GetUserResponse>>
    suspend fun getUserRepos(username: String, perPage: Int): Flow<Resource<GetUserReposResponse>>
    suspend fun getSearchUser(q: String, perPage: Int): Flow<Resource<GetUserResponse>>
}