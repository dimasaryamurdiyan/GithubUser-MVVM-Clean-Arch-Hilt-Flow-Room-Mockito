package com.testcase.githubapp.ui

import android.app.ProgressDialog
import androidx.fragment.app.Fragment
import com.testcase.githubapp.utils.Utils

abstract class BaseFragment : Fragment() {
    protected var progressDialog: ProgressDialog? = null

    fun hideLoading() {
        if (progressDialog != null && progressDialog?.isShowing!!) {
            progressDialog?.cancel()
        }
    }

    fun showLoading() {
        hideLoading()
        progressDialog = Utils.showLoadingDialog(this.context)
    }
}