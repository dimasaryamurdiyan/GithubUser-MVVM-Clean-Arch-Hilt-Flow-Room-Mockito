package com.testcase.githubapp.data.remote

import com.testcase.githubapp.data.Resource
import com.testcase.githubapp.data.remote.network.ApiService
import com.testcase.githubapp.data.remote.response.GetSearchUserResponse
import com.testcase.githubapp.data.remote.response.GetUserReposResponse
import com.testcase.githubapp.data.remote.response.GetUserResponse
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiService: ApiService): BaseDataSource() {
    suspend fun getUsers(): Resource<GetUserResponse> = safeCallResult { apiService.getUsers() }

    suspend fun getSearchUsers(q: String, perPage: Int): Resource<GetSearchUserResponse> = safeCallResult { apiService.getSearchUsers(q, perPage) }

    suspend fun getUser(username: String): Resource<GetUserResponse.User> = safeCallResult { apiService.getUser(username) }

    suspend fun getUserFollowers(username: String): Resource<GetUserResponse> = safeCallResult { apiService.getUserFollowers(username) }

    suspend fun getUserFollowing(username: String): Resource<GetUserResponse> = safeCallResult { apiService.getUserFollowing(username) }

    suspend fun getUserRepos(username: String, perPage: Int): Resource<GetUserReposResponse> = safeCallResult { apiService.getUserRepos(username, perPage) }
}