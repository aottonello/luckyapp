package com.lucky.app.utils

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.text.DecimalFormat
import java.text.NumberFormat


fun String?.strToLowerCase(): String {
    return this.toString().toLowerCase()
}

fun String?.concatString(value: Int): String {
    return "$value" + " " + this.toString()
}

fun <T> Single<T>.applySchedulers(): Single<T> {
    return subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}


/**
 * Allow apply format to numbers, eg: 190000 format 190k
 * @param number: number
 * @return: string formatted
 */
fun formatFloatingPoint(number: Int): String? {
    var number = number
    val power: Int
    val suffix = " kmbt"
    var formattedNumber = ""
    val formatter: NumberFormat = DecimalFormat("#,###.#")
    power = StrictMath.log10(number.toDouble()).toInt()
    number = (number / Math.pow(10.0, (power / 3 * 3).toDouble())).toInt()
    formattedNumber = formatter.format(number.toLong())
    formattedNumber = formattedNumber + suffix[power / 3]
    return if (formattedNumber.length > 4) formattedNumber.replace(
        "\\.[0-9]+".toRegex(),
        ""
    ) else formattedNumber
}