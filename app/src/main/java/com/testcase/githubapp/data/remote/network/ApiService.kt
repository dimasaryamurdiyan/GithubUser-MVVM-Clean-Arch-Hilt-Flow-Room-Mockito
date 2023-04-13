package com.testcase.githubapp.data.remote.network

import com.testcase.githubapp.data.remote.response.GetSearchUserResponse
import com.testcase.githubapp.data.remote.response.GetUserReposResponse
import com.testcase.githubapp.data.remote.response.GetUserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("users")
    suspend fun getUsers(): Response<GetUserResponse>

    @GET("users/{username}")
    suspend fun getUser(@Path("username") username: String): Response<GetUserResponse.User>

    @GET("users/{username}/followers")
    suspend fun getUserFollowers(@Path("username") username: String): Response<GetUserResponse>

    @GET("users/{username}/following")
    suspend fun getUserFollowing(@Path("username") username: String): Response<GetUserResponse>

    @GET("users/{username}/repos")
    suspend fun getUserRepos(
        @Path("username") username: String,
        @Query("per_page") per_page: Int? = null
    ): Response<GetUserReposResponse>

    @GET("search/users")
    suspend fun getSearchUsers(
        @Query("q") q: String,
        @Query("per_page") per_page: Int? = null
    ): Response<GetSearchUserResponse>


}