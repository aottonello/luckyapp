package com.lucky.app.repositories

import android.content.Context
import com.lucky.app.models.LuckyHomeModel
import com.lucky.app.models.LuckyItemDetailModel
import com.lucky.app.services.LuckyService
import com.lucky.app.utils.LuckyGsonMappersUtils
import io.reactivex.Single
import javax.inject.Inject

/**
 * Repository persistent data model and a remote backend data source.
 * 1.Service call
 * 2.Load json data from file
 *
 */
class LuckyRepositoryImpl @Inject constructor(
    private val luckyService: LuckyService
) : LuckyRepository {

    override fun getLuckyHomeSections(): Single<LuckyHomeModel> {
        return luckyService.getLuckyHomeSections()
    }

    override fun getDetailItem(context: Context): Single<LuckyItemDetailModel> {
        val strMock = LuckyGsonMappersUtils.getJsonFromAssets(context, "mock_detail.json")
        return Single.just(
            LuckyGsonMappersUtils.mapJsonToModelFromString(
                strMock,
                LuckyItemDetailModel::class.java
            )
        )
    }
}