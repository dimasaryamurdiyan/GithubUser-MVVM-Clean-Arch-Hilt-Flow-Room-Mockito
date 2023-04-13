package com.testcase.githubapp.utils

import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import androidx.core.graphics.drawable.toDrawable
import com.testcase.githubapp.R
import java.text.SimpleDateFormat
import java.util.*

object Utils {
    const val UTCT_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    const val DATE_HALF_MONTH_YEARS_FORMAT = "dd MMM yyyy"

    fun showLoadingDialog(context: Context?): ProgressDialog? {
        context?.let {
            val progressDialog = ProgressDialog(context)
            progressDialog.let {
                it.show()
                it.window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
                it.setContentView(R.layout.layout_progress_dialog)
                it.isIndeterminate = true
                it.setCancelable(false)
                it.setCanceledOnTouchOutside(false)
                return it
            }
        }
        return null
    }

    fun convertUTCTtoDateHalfMonthYears(dateTime: String?): String {
        return if (!dateTime.isNullOrEmpty()) {
            val pattern = SimpleDateFormat(UTCT_FORMAT)
            val outputFormat = SimpleDateFormat(DATE_HALF_MONTH_YEARS_FORMAT, Locale("id", "ID"))

            outputFormat.format(pattern.parse(dateTime))
        } else {
            ""
        }
    }
}