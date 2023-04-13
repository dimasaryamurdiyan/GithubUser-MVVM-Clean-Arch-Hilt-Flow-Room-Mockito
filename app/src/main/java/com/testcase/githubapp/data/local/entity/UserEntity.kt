package com.testcase.githubapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey
    val id: Int,
    val login: String? = null,
    val nodeId: String? = null,
    val avatarUrl: String?= null,
    val gravatarId: String?= null,
    val url: String?= null,
    val htmlUrl: String?= null,
    val followersUrl: String?= null,
    val followingUrl: String? = null,
    val gistsUrl: String?= null,
    val starredUrl: String?= null,
    val subscriptionsUrl: String?= null,
    val organizationsUrl: String?= null,
    val reposUrl: String?= null,
    val eventsUrl: String?= null,
    val receivedEventsUrl: String?= null,
    val type: String?= null,
    val siteAdmin: Boolean?= null,
    val name: String?= null,
    val company: String?= null,
    val blog: String?= null,
    val location: String?= null,
    val email: String?= null,
    val hireable: String?= null,
    val bio: String?= null,
    val twitterUsername: String?= null,
    val publicRepos: Int?= null,
    val publicGists: Int?= null,
    val followers: Int?= null,
    val following: Int?= null,
    val createdAt: String?= null,
    val updatedAt: String?= null,
)