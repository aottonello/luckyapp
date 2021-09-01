package com.lucky.app.actions

import com.lucky.app.models.LuckyHomeModel
import com.lucky.app.models.LuckyItemDetailModel

/**
 * Actions used to interact vieModel with views
 */
sealed class LuckyActions {
    data class SectionsHome(val data: LuckyHomeModel) : LuckyActions()
    data class ItemDetail(val data: LuckyItemDetailModel) : LuckyActions()
}