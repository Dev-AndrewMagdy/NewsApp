package com.news.task.newsapp.domain.utils

interface ValueEnum<T> {
    val value: T
}

inline fun <reified V : ValueEnum<out T>, T> findValueEnum(value: T) =
    V::class.java.enumConstants?.find { it.value == value }
        ?: throw IllegalStateException("Unknown value")