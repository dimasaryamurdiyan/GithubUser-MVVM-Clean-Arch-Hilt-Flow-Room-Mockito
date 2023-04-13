package com.testcase.githubapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.testcase.githubapp.data.Resource
import com.testcase.githubapp.data.remote.response.GetUserResponse
import com.testcase.githubapp.domain.usecase.UserUseCase
import com.testcase.githubapp.ui.user.UserViewModel
import id.co.binar.rule.TestCoroutineRule
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class UserViewModelTest: TestCase() {
    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var viewModel: UserViewModel

    @Mock
    private lateinit var userUseCase: UserUseCase

    @Before
    public override fun setUp() {
        viewModel = UserViewModel(userUseCase)
    }

    @Test
    fun `getUsers success`() = testCoroutineRule.runBlockingTest {
        val listData = GetUserResponse()

        listData.addAll(
            mutableListOf(
                GetUserResponse.User(
                    id = 1,
                    login = "dimas"
                )
            )
        )
        val expectedResult = Resource.success(
            data = listData
        )

        val channel = Channel<Resource<GetUserResponse>>()
        val flow = channel.consumeAsFlow()

        Mockito.`when`(userUseCase.getUsers()).thenReturn(flow)

        launch {
            channel.send(expectedResult)
        }

        viewModel.getUsers()

        val result = viewModel.userList.value
        assertEquals(expectedResult.data, result?.data)
    }

    @Test
    fun `getUsers error`() = testCoroutineRule.runBlockingTest {

        val expectedResult = Resource.error(
            message = "gagal",
            data = null
        )

        val channel = Channel<Resource<GetUserResponse>>()
        val flow = channel.consumeAsFlow()

        Mockito.`when`(userUseCase.getUsers()).thenReturn(flow)

        launch {
            channel.send(expectedResult)
        }

        viewModel.getUsers()

        val result = viewModel.userList.value
        assertEquals(expectedResult.message, result?.message)
    }

    @Test
    fun `getSearchUsers success`() = testCoroutineRule.runBlockingTest {
        val q = "Dimas"
        val perPage = 3
        val listData = GetUserResponse()

        listData.addAll(
            mutableListOf(
                GetUserResponse.User(
                    id = 1,
                    login = "dimas"
                )
            )
        )
        val expectedResult = Resource.success(
            data = listData
        )

        val channel = Channel<Resource<GetUserResponse>>()
        val flow = channel.consumeAsFlow()

        Mockito.`when`(userUseCase.getSearchUser(q, perPage)).thenReturn(flow)

        launch {
            channel.send(expectedResult)
        }

        viewModel.getSearchUsers(q)

        val result = viewModel.userList.value
        assertEquals(expectedResult.data, result?.data)
        assertEquals(expectedResult.data?.size, result?.data?.size)
    }

    @Test
    fun `getSearchUsers error`() = testCoroutineRule.runBlockingTest {
        val q = "Dimas"
        val perPage = 3
        val expectedResult = Resource.error(
            message = "gagal",
            data = null
        )

        val channel = Channel<Resource<GetUserResponse>>()
        val flow = channel.consumeAsFlow()

        Mockito.`when`(userUseCase.getSearchUser(q, perPage)).thenReturn(flow)

        launch {
            channel.send(expectedResult)
        }

        viewModel.getSearchUsers(q)

        val result = viewModel.userList.value
        assertEquals(expectedResult.message, result?.message)
    }

}