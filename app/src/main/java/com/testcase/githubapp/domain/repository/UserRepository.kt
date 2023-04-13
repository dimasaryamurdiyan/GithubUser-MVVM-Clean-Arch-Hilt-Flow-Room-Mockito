package com.testcase.githubapp.domain.repository

import android.util.Log
import com.testcase.githubapp.data.Resource
import com.testcase.githubapp.data.local.LocalDataSource
import com.testcase.githubapp.data.local.entity.UserEntity
import com.testcase.githubapp.data.remote.RemoteDataSource
import com.testcase.githubapp.data.remote.response.GetSearchUserResponse
import com.testcase.githubapp.data.remote.response.GetUserReposResponse
import com.testcase.githubapp.data.remote.response.GetUserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
): IUserRepository {
    override suspend fun getUsers(forceUpdate: Boolean): Flow<Resource<GetUserResponse>> {
        return flow {
            emit(getUsersCached())
            val resultLocal = getUsersCached()

            if(resultLocal.data.isNullOrEmpty()) {
                emit(Resource.loading())
                val result = remoteDataSource.getUsers()

                //Cache to database if response is successful
                if (result.status == Resource.Status.SUCCESS) {
                    result.data?.let { it ->
                        val list = ArrayList<UserEntity>()
                        it.forEach { user ->
                            val resultUser = remoteDataSource.getUser(user.login ?: "")
                            if (resultUser.status == Resource.Status.SUCCESS){
                                val data = resultUser.data
                                val userEntity = UserEntity(
                                    id = user.id ?: 0,
                                    login = data?.login,
                                    name = data?.name,
                                    company = data?.company,
                                    email = data?.email,
                                    location = data?.location,
                                    avatarUrl = data?.avatarUrl
                                )
                                list.add(userEntity)
                            }
                        }
                        localDataSource.insertAllUsers(list)
                    }
                }
            }
            emit(getUsersCached())
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getUser(username: String): Flow<Resource<GetUserResponse.User>> {
        return flow {
            emit(Resource.loading())
            emit(remoteDataSource.getUser(username))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getUserFollowers(username: String): Flow<Resource<GetUserResponse>> {
        return flow {
            emit(Resource.loading())
            emit(remoteDataSource.getUserFollowers(username))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getUserFollowing(username: String): Flow<Resource<GetUserResponse>> {
        return flow {
            emit(Resource.loading())
            emit(remoteDataSource.getUserFollowing(username))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getUserRepos(
        username: String,
        perPage: Int
    ): Flow<Resource<GetUserReposResponse>> {
        return flow {
            emit(Resource.loading())
            emit(
                remoteDataSource.getUserRepos(
                    username,
                    perPage
                )
            )
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getSearchUsers(
        q: String,
        perPage: Int
    ): Flow<Resource<GetUserResponse>> {
        return flow {
            emit(Resource.loading())
            val result = remoteDataSource.getSearchUsers(q, perPage)
            val listResponse = GetUserResponse()
            //Cache to database if response is successful
            if (result.status == Resource.Status.SUCCESS) {
                result.data?.let { it ->
                    val list = ArrayList<UserEntity>()

                    Log.d("UserRepository","cached: $list")
                    Resource.success(list)
                    it.items?.forEach { user ->
                        val resultUser = remoteDataSource.getUser(user?.login ?: "")
                        if (resultUser.status == Resource.Status.SUCCESS){
                            val data = resultUser.data
                            val userEntity = UserEntity(
                                id = user?.id ?: 0,
                                login = data?.login,
                                name = data?.name,
                                company = data?.company,
                                email = data?.email,
                                location = data?.location,
                                avatarUrl = data?.avatarUrl
                            )
                            val userResponse = GetUserResponse.User(
                                id = user?.id ?: 0,
                                login = data?.login,
                                name = data?.name,
                                company = data?.company,
                                email = data?.email,
                                location = data?.location,
                                avatarUrl = data?.avatarUrl
                            )
                            listResponse.add(userResponse)
                            list.add(userEntity)
                        }
                    }
//                    localDataSource.deleteAllUsers(resultLocal)
                    localDataSource.insertAllUsers(list)
                }
            }

            emit(Resource.success(listResponse))
        }.flowOn(Dispatchers.IO)
    }

    private fun getUsersCached(): Resource<GetUserResponse> =
        localDataSource.getUsers().let { users ->
            val list = GetUserResponse()
            users.forEach { it ->
                val user = GetUserResponse.User(
                    id = it.id,
                    login = it.login,
                    name = it.name,
                    company = it.company,
                    email = it.email,
                    location = it.location,
                    avatarUrl = it.avatarUrl
                )
                list.add(user)
            }
            Resource.success(list)
        }
}