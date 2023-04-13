package com.testcase.githubapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.testcase.githubapp.data.Resource
import com.testcase.githubapp.data.remote.response.GetUserReposResponse
import com.testcase.githubapp.data.remote.response.GetUserResponse
import com.testcase.githubapp.domain.usecase.UserUseCase
import com.testcase.githubapp.ui.detailuser.DetailUserViewModel
import com.testcase.githubapp.ui.user.UserViewModel
import id.co.binar.rule.TestCoroutineRule
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DetailUserViewModelTest: TestCase() {
    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var viewModel: DetailUserViewModel

    @Mock
    private lateinit var userUseCase: UserUseCase

    @Before
    public override fun setUp() {
        viewModel = DetailUserViewModel(userUseCase)
    }

    @Test
    fun `getUserRepos success`() = testCoroutineRule.runBlockingTest {
        val username = "dimas"
        val perPage = 3
        val listData = GetUserReposResponse()

        listData.addAll(
            mutableListOf(
                GetUserReposResponse.GetUserReposResponseItem(
                    id = 1,
                )
            )
        )
        val expectedResult = Resource.success(
            data = listData
        )

        val channel = Channel<Resource<GetUserReposResponse>>()
        val flow = channel.consumeAsFlow()

        Mockito.`when`(userUseCase.getUserRepos(username, perPage)).thenReturn(flow)

        launch {
            channel.send(expectedResult)
        }

        viewModel.getUserRepos(username)

        val result = viewModel.userRepos.value
        assertEquals(expectedResult.data, result?.data)
        assertEquals(expectedResult.data?.size, result?.data?.size)
    }

    @Test
    fun `getUserRepos error`() = testCoroutineRule.runBlockingTest {
        val username = "dimas"
        val perPage = 3
        val expectedResult = Resource.error(
            message = "gagal",
            data = null
        )

        val channel = Channel<Resource<GetUserReposResponse>>()
        val flow = channel.consumeAsFlow()

        Mockito.`when`(userUseCase.getUserRepos(username, perPage)).thenReturn(flow)

        launch {
            channel.send(expectedResult)
        }

        viewModel.getUserRepos(username)

        val result = viewModel.userRepos.value
        assertEquals(expectedResult.message, result?.message)
    }

    @Test
    fun `getUser success`() = testCoroutineRule.runBlockingTest {
        val username = "Dimas"

        val expectedResult = Resource.success(
            data = GetUserResponse.User(
                id = 1
            )
        )

        val channel = Channel<Resource<GetUserResponse.User>>()
        val flow = channel.consumeAsFlow()

        Mockito.`when`(userUseCase.getUser(username)).thenReturn(flow)

        launch {
            channel.send(expectedResult)
        }

        viewModel.getUser(username)

        val result = viewModel.user.value
        assertEquals(expectedResult.data, result?.data)
    }

    @Test
    fun `getUser error`() = testCoroutineRule.runBlockingTest {
        val username = "Dimas"

        val expectedResult = Resource.error(
            message = "gagal",
            data = null
        )

        val channel = Channel<Resource<GetUserResponse.User>>()
        val flow = channel.consumeAsFlow()

        Mockito.`when`(userUseCase.getUser(username)).thenReturn(flow)

        launch {
            channel.send(expectedResult)
        }

        viewModel.getUser(username)

        val result = viewModel.user.value
        assertEquals(expectedResult.message, result?.message)
    }
}