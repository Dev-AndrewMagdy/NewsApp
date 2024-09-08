package com.news.task.newsapp.data.remote.networking

import java.net.InetAddress

object NetworkAvailable {
    fun isNetworkAvailable(): Boolean {
        return try {
            val ipAddress = InetAddress.getByName("google.com")
            ipAddress.toString() != ""
        } catch (e: Exception) {
            false
        }
    }
}