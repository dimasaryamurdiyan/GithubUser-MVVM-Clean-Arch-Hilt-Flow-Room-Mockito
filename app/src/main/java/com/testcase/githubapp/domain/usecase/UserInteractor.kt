package com.testcase.githubapp.domain.usecase

import com.testcase.githubapp.data.Resource
import com.testcase.githubapp.data.remote.response.GetUserReposResponse
import com.testcase.githubapp.data.remote.response.GetUserResponse
import com.testcase.githubapp.domain.repository.IUserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 *   Interactor class
 */
class UserInteractor @Inject constructor(private val userRepository: IUserRepository): UserUseCase {
    override suspend fun getUsers(forceUpdate: Boolean): Flow<Resource<GetUserResponse>> = userRepository.getUsers(forceUpdate)
    override suspend fun getUser(username: String): Flow<Resource<GetUserResponse.User>> {
        return userRepository.getUser(username)
    }

    override suspend fun getUserFollowers(username: String): Flow<Resource<GetUserResponse>> {
        return userRepository.getUserFollowers(username)
    }

    override suspend fun getUserFollowing(username: String): Flow<Resource<GetUserResponse>> {
        return userRepository.getUserFollowing(username)
    }

    override suspend fun getUserRepos(
        username: String,
        perPage: Int
    ): Flow<Resource<GetUserReposResponse>> {
        return userRepository.getUserRepos(
            username, perPage
        )
    }

    override suspend fun getSearchUser(
        q: String,
        perPage: Int
    ): Flow<Resource<GetUserResponse>> {
        return userRepository.getSearchUsers(q, perPage)
    }
}