package com.fakhari.batmanmovie.data.network.interceptor


import com.fakhari.batmanmovie.utility.ConstValue
import okhttp3.Interceptor
import okhttp3.Response

class BatmanInterceptor(): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response: Response?


        val newRequest = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer ${ConstValue.API_KEY}")
                .build()
        response = chain.proceed(newRequest)

        return response
    }
}