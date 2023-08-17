package com.fakhari.batmanmovie.data.network.retrofit


import com.fakhari.batmanmovie.utility.GsonTools
import com.fakhari.batmanmovie.data.network.api.ApiService
import com.fakhari.batmanmovie.utility.ConstValue
import com.fakhari.batmanmovie.utility.ToStringConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class RetrofitRequest {

    private var retrofit: Retrofit? = null

    fun getApiRequest(): ApiService {

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(ConstValue.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient.build())
                .addConverterFactory(ToStringConverterFactory.getInstance())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(GsonTools.getInstanceExposeAnnotation()))
                .build();
        }
        return retrofit!!.create(ApiService::class.java)
    }
}