package com.sport.project.fitapp.network.api

import com.sport.project.fitapp.utils.Constants
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class AuthInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("x-app-id", Constants.NUTRITIONIX_APP_ID)
            .addHeader("x-app-key", Constants.NUTRITIONIX_APP_KEY)
            .build()
        return chain.proceed(request)
    }
}