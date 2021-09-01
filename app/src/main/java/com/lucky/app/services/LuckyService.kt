package com.lucky.app.services

import com.lucky.app.models.LuckyHomeModel
import io.reactivex.Single
import retrofit2.http.GET

interface LuckyService {

    @GET("/test.json")
    fun getLuckyHomeSections(): Single<LuckyHomeModel>

}