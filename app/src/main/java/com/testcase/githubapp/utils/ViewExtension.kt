package com.testcase.githubapp.utils

import android.os.Looper
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.CheckResult
import androidx.core.widget.doOnTextChanged
import com.bumptech.glide.Glide
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.onStart

fun ImageView.loadImage(url: String) {
    Glide
        .with(this)
        .load(url)
        .into(this)
}

fun TextView.compoundDrawable(start: Int = 0, top: Int = 0, end: Int = 0, bottom: Int = 0) =
    setCompoundDrawablesWithIntrinsicBounds(
        start, top, end, bottom
    )

@ExperimentalCoroutinesApi
@CheckResult
fun EditText.textChanges(): Flow<CharSequence?> {
    return callbackFlow {
        checkMainThread()

        val listener = doOnTextChanged { text, _, _, _ -> trySend(text) }
        awaitClose { removeTextChangedListener(listener) }
    }.onStart { emit(text) }
}

internal fun checkMainThread() {
    check(Looper.myLooper() == Looper.getMainLooper()) {
        "Expected to be called on the main thread but was " + Thread.currentThread().name
    }
}