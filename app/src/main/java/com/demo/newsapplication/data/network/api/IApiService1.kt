package com.muzville.data.network.api

import com.demo.newsapplication.BuildConfig
import com.demo.newsapplication.models.NewsModuls
import com.demo.newsapplication.ui.newslist.NewsViewModel
import com.google.gson.GsonBuilder
import com.muzville.data.network.api.IBaseService.Companion.getOkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

interface IApiService1 : IBaseService {
    @GET("everything?q=tesla&from=2022-02-23&sortBy=publishedAt&apiKey=fd21a6cdffa44b4188037d1a94175f65")
    suspend fun getNationalityOptions(): Response<NewsModuls>


    companion object {
        fun getService(needEncrypt: Boolean): IApiService1 {
            return Retrofit.Builder()
                .baseUrl(BuildConfig.BaseUrl)
                .client(getOkHttpClient(needEncrypt))
                .addConverterFactory(
                    GsonConverterFactory.create(
                        GsonBuilder().setLenient().create()
                    )
                )
                .addConverterFactory(ScalarsConverterFactory.create())
                .build().create(IApiService1::class.java)
        }
    }
}
