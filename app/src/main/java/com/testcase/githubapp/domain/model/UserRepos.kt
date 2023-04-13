package com.testcase.githubapp.domain.model

import com.google.gson.annotations.SerializedName
import com.testcase.githubapp.data.remote.response.GetUserReposResponse

class UserRepos : ArrayList<UserRepos.UserReposItem>(){
    data class UserReposItem(
        val allowForking: Boolean? = null,
        val archiveUrl: String? = null,
        val archived: Boolean? = null,
        val assigneesUrl: String? = null,
        val blobsUrl: String? = null,
        val branchesUrl: String? = null,
        val cloneUrl: String? = null,
        val collaboratorsUrl: String? = null,
        val commentsUrl: String? = null,
        val commitsUrl: String? = null,
        val compareUrl: String? = null,
        val contentsUrl: String? = null,
        val contributorsUrl: String? = null,
        val createdAt: String? = null,
        val defaultBranch: String? = null,
        val deploymentsUrl: String? = null,
        val description: String? = null,
        val disabled: Boolean? = null,
        val downloadsUrl: String? = null,
        val eventsUrl: String? = null,
        val fork: Boolean? = null,
        val forks: Int? = null,
        val forksCount: Int? = null,
        val forksUrl: String? = null,
        val fullName: String? = null,
        val gitCommitsUrl: String? = null,
        val gitRefsUrl: String? = null,
        val gitTagsUrl: String? = null,
        val gitUrl: String? = null,
        val hasDownloads: Boolean? = null,
        val hasIssues: Boolean? = null,
        val hasPages: Boolean? = null,
        val hasProjects: Boolean? = null,
        val hasWiki: Boolean? = null,
        val homepage: String? = null,
        val hooksUrl: String? = null,
        val htmlUrl: String? = null,
        val id: Int? = null,
        val isTemplate: Boolean? = null,
        val issueCommentUrl: String? = null,
        val issueEventsUrl: String? = null,
        val issuesUrl: String? = null,
        val keysUrl: String? = null,
        val labelsUrl: String? = null,
        val language: String? = null,
        val languagesUrl: String? = null,
        val license: License? = null,
        val mergesUrl: String? = null,
        val milestonesUrl: String? = null,
        val mirrorUrl: Any? = null,
        val name: String? = null,
        val nodeId: String? = null,
        val notificationsUrl: String? = null,
        val openIssues: Int? = null,
        val openIssuesCount: Int? = null,
        val owner: GetUserReposResponse.GetUserReposResponseItem.Owner? = null,
        val private: Boolean? = null,
        val pullsUrl: String? = null,
        val pushedAt: String? = null,
        val releasesUrl: String? = null,
        val size: Int? = null,
        val sshUrl: String? = null,
        val stargazersCount: Int? = null,
        val stargazersUrl: String? = null,
        val statusesUrl: String? = null,
        val subscribersUrl: String? = null,
        val subscriptionUrl: String? = null,
        val svnUrl: String? = null,
        val tagsUrl: String? = null,
        val teamsUrl: String? = null,
        val topics: List<Any?>? = null,
        val treesUrl: String? = null,
        val updatedAt: String? = null,
        val url: String? = null,
        val visibility: String? = null,
        val watchers: Int? = null,
        val watchersCount: Int? = null,
        val webCommitSignoffRequired: Boolean? = null
    ) {
        data class License(
            val key: String? = null,
            val name: String? = null,
            val nodeId: String? = null,
            val spdxId: String? = null,
            val url: Any? = null
        )

        data class Owner(
            val avatarUrl: String? = null,
            val eventsUrl: String? = null,
            val followersUrl: String? = null,
            val followingUrl: String? = null,
            val gistsUrl: String? = null,
            val gravatarId: String? = null,
            val htmlUrl: String? = null,
            val id: Int? = null,
            val login: String? = null,
            val nodeId: String? = null,
            val organizationsUrl: String? = null,
            val receivedEventsUrl: String? = null,
            val reposUrl: String? = null,
            val siteAdmin: Boolean? = null,
            val starredUrl: String? = null,
            val subscriptionsUrl: String? = null,
            val type: String? = null,
            val url: String? = null
        )
    }
}