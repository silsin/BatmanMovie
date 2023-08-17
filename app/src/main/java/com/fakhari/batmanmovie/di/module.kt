package com.fakhari.batmanmovie.di


import com.fakhari.batmanmovie.BuildConfig
import com.fakhari.batmanmovie.data.network.api.ApiService
import com.fakhari.batmanmovie.data.network.interceptor.BatmanInterceptor
import com.fakhari.batmanmovie.ui.moviedetail.MovieDetailViewModel
import com.fakhari.batmanmovie.ui.movielist.MovieListVieModel
import com.fakhari.batmanmovie.utility.ConstValue
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {
    //creating WebService and interceptor
    single(named("batmanInterceptor")) { BatmanInterceptor() as Interceptor }

    single(named("batmanClient")) {
        createOkHttpClient(
            get(named("batmanInterceptor")),
        )
    }
    single(named("apiClient")) {
        createOkHttpClient(
            get(named("batmanInterceptor")),
        )
    }
    single {
        createWebService<ApiService>(
            get(named("apiClient")),
            ConstValue.BASE_URL
        )
    }
}
val dataModule = module {
    viewModel { MovieListVieModel(get()) }
viewModel { MovieDetailViewModel(get()) }
}
fun createOkHttpClient(vararg interceptors: Interceptor?): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

    val timeout = 30L
    val client = OkHttpClient.Builder()
        .connectTimeout(timeout, TimeUnit.SECONDS)
        .readTimeout(timeout, TimeUnit.SECONDS)


    for (interceptor in interceptors) {
        if (interceptor != null)
            client.addInterceptor(interceptor)

    }

    if (BuildConfig.DEBUG) {
        client.addInterceptor(httpLoggingInterceptor)
    }


    return client.build()
}


// Creating the webservice
inline fun <reified T> createWebService(okHttpClient: OkHttpClient, url: String): T {
    val gson = GsonBuilder()
        .setDateFormat("yyyy-MM-dd HH:mm:ss")
        .create()

    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))

        .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
    return retrofit.create(T::class.java)
}


var allModules = listOf(dataModule, appModule)