package com.lucky.app.repositories

import android.content.Context
import com.lucky.app.models.LuckyHomeModel
import com.lucky.app.models.LuckyItemDetailModel
import io.reactivex.Single

interface LuckyRepository {

  fun getLuckyHomeSections(): Single<LuckyHomeModel>

  fun getDetailItem(context: Context): Single<LuckyItemDetailModel>

}