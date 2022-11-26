package com.devwarex.news.util

object TimeoutUtil {

    //since we're on free subscription, and it update articles every 1 hour
    //and we've limit of requests.
    //we'll update articles every 1 hour
    private const val articles_ttl: Long = 3_600_000
    fun isTimeout(currentTime: Long,savedTime: Long): Boolean = (currentTime - savedTime) > articles_ttl
}