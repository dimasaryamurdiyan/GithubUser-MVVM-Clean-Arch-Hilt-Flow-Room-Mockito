package com.testcase.githubapp

import com.google.gson.GsonBuilder
import com.testcase.githubapp.data.remote.RemoteDataSource
import com.testcase.githubapp.data.remote.network.ApiService
import com.testcase.githubapp.data.remote.network.RequestInterceptor
import com.testcase.githubapp.data.remote.response.GetUserResponse
import com.testcase.githubapp.helper.enqueueResponse
import com.testcase.githubapp.utils.Constants
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.isActive
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@ExperimentalCoroutinesApi
@ExperimentalSerializationApi
class RemoteDataSourceTest {
    private lateinit var mockWebServer: MockWebServer

    lateinit var apiService: ApiService

    private lateinit var okHttpClient: OkHttpClient

    private lateinit var loggingInterceptor: HttpLoggingInterceptor

    private lateinit var remoteDataSource: RemoteDataSource


    @Before
    fun setup(){
        mockWebServer = MockWebServer()

        mockWebServer.start()

        loggingInterceptor = HttpLoggingInterceptor().apply{
            level = HttpLoggingInterceptor.Level.BODY
        }
        okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()

         apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url(Constants.BASE_URL))
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(ApiService::class.java)

         remoteDataSource = RemoteDataSource(apiService)
    }


    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `should fetch users correctly given 200 response`() {

        runBlocking {
            mockWebServer.enqueueResponse("get_users_200.json", 200, 3000)

            val actualResponse = apiService.getUsers().body()

            val actual = remoteDataSource.getUsers()

            val expected = mutableListOf<GetUserResponse.User>(
                GetUserResponse.User(
                    id = 1,
                    login = "mojombo"
                )
            )

            val data = actual.data?.get(0)?.login
            assertEquals(expected[0].login, actualResponse?.get(0)?.login)
        }
    }
}