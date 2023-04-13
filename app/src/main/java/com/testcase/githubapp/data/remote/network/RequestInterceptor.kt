package com.testcase.githubapp.data.remote.network

import com.testcase.githubapp.utils.Constants
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class RequestInterceptor() :
    Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val token = Constants.GITHUB_TOKEN

        val request = original.newBuilder()
            .header("Authorization", "Bearer $token")
            .header("Accept", "application/vnd.github+json")
            .method(original.method, original.body)
            .build()

        return chain.proceed(request)
    }
}