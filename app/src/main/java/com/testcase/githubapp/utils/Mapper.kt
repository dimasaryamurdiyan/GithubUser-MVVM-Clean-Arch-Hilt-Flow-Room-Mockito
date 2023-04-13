package com.testcase.githubapp.utils

interface Mapper<T : Any, Model : Any> {
    fun toModel(value: T): Model
    fun fromModel(value: Model): T
}