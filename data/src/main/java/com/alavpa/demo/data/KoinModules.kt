package com.alavpa.demo.data

import androidx.room.Room
import com.alavpa.demo.data.api.ApiClient
import com.alavpa.demo.data.api.ApiDataSourceImpl
import com.alavpa.demo.data.database.DbDataSourceImpl
import com.alavpa.demo.data.database.DemoDb
import com.alavpa.demo.domain.datasource.ApiDataSource
import com.alavpa.demo.domain.datasource.DbDataSource
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val apiModule = module {
    single { GsonBuilder().create() }
    single { httpLoggingInterceptor() }
    single { okHttpClient(httpLoggingInterceptor = get()) }
    single { demoApi(client = get(), gson = get()) }
    single { ApiDataSourceImpl(apiClient = get()) } bind ApiDataSource::class
}

fun okHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient = OkHttpClient.Builder()
    .addInterceptor(httpLoggingInterceptor)
    .readTimeout(10, TimeUnit.SECONDS)
    .callTimeout(10, TimeUnit.SECONDS)
    .connectTimeout(10, TimeUnit.SECONDS)
    .build()

fun httpLoggingInterceptor(): HttpLoggingInterceptor = if (BuildConfig.DEBUG) {
    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
} else {
    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
}

fun demoApi(client: OkHttpClient, gson: Gson): ApiClient = Retrofit.Builder()
    .client(client)
    .baseUrl(ApiClient.BASE_URL)
    .addConverterFactory(GsonConverterFactory.create(gson))
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .build()
    .create(ApiClient::class.java)

val dbModule = module {
    single { Room.databaseBuilder(androidApplication(), DemoDb::class.java, DemoDb.DB_NAME).build() }
    single { DbDataSourceImpl(db = get()) } bind DbDataSource::class
}
