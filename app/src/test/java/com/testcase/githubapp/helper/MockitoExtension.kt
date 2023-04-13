package com.testcase.githubapp.helper

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.mockito.Mockito
import java.nio.charset.StandardCharsets
import java.util.concurrent.TimeUnit

/**
 * Helper functions that are workarounds to kotlin Runtime Exceptions when using kotlin.
 */




/**
 * Returns Mockito.any() as nullable type to avoid java.lang.IllegalStateException when
 * null is returned.
 */
fun <T> any(): T = Mockito.any<T>()

internal fun MockWebServer.enqueueResponse(fileName: String, code: Int, delayMillis: Long = 0L) {
    val inputStream = javaClass.classLoader?.getResourceAsStream("api-response/$fileName")

    val source = inputStream?.let { inputStream.source().buffer() }
    val mockResponse = source?.let {
        MockResponse()
        .setResponseCode(code)
        .setBody(it.readString(StandardCharsets.UTF_8))
    }

    if (delayMillis > 0L) {
        mockResponse?.setBodyDelay(delayMillis, TimeUnit.MILLISECONDS)
    }

    mockResponse?.let { enqueue(it) }

}

