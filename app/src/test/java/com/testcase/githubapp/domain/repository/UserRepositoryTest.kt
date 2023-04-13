package com.testcase.githubapp.domain.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.testcase.githubapp.data.Resource
import com.testcase.githubapp.data.local.LocalDataSource
import com.testcase.githubapp.data.local.entity.UserEntity
import com.testcase.githubapp.data.remote.RemoteDataSource
import com.testcase.githubapp.data.remote.response.GetUserResponse
import id.co.binar.rule.TestCoroutineRule
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class UserRepositoryTest: TestCase(){

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var localDataSource: LocalDataSource

    @Mock
    private lateinit var remoteDataSource: RemoteDataSource

    private lateinit var userRepository: UserRepository

    @Before
    public override fun setUp() {
        userRepository = UserRepository(localDataSource, remoteDataSource)
    }

    @Test
    fun `getUsers should return data from localDataSource if available`() = runBlocking {
        // Arrange
        val expectedData = GetUserResponse()
        expectedData.add(GetUserResponse.User(id = 1, login = "user1", name = "User One"))
        expectedData.add(GetUserResponse.User(id = 2, login = "user2", name = "User Two"))
        Mockito.`when`(localDataSource.getUsers()).thenReturn(expectedData.map { user ->
            UserEntity(
                id = user.id ?: -1,
                login = user.login,
                name = user.name,
                company = user.company,
                email = user.email,
                location = user.location,
                avatarUrl = user.avatarUrl
            )
        })

        // Act
        val result = userRepository.getUsers(forceUpdate = false).first()

        // Assert
        Assert.assertTrue(result.status == Resource.Status.SUCCESS)
        Assert.assertEquals(expectedData, result.data)
    }

}