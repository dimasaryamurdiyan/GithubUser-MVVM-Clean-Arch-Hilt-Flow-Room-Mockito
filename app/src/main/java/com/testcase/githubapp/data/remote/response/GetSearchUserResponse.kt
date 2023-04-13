package com.testcase.githubapp.data.remote.response


import com.google.gson.annotations.SerializedName

data class GetSearchUserResponse(
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean? = null,
    @SerializedName("items")
    val items: List<GetUserResponse.User?>? = null,
    @SerializedName("total_count")
    val totalCount: Int? = null
)