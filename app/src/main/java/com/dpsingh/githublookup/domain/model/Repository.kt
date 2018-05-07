package com.dpsingh.githublookup.domain.model

import com.google.gson.annotations.SerializedName

data class Repository(
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String,
        @SerializedName("owner")
        val owner: User,
        @SerializedName("description")
        val description: String?,
        @SerializedName("url")
        val url: String?,
        @SerializedName("stargazers_count")
        val stargazers_count: Int = 0,
        @SerializedName("watchers_count")
        val watchers_count: Int = 0,
        @SerializedName("forks_count")
        val forks_count: Int = 0,
        @SerializedName("language")
        val language: String,
        @SerializedName("has_issues")
        val has_issues: Boolean,
        @SerializedName("open_issues_count")
        val open_issues_count: Int,
        @SerializedName("watchers")
        val watchers: Int
)
