package com.lucky.app.utils

import android.content.Context
import com.google.gson.Gson
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream

/**
 * Utils Gson
 * 1.Read json file
 * 2.Map json to models
 */
object LuckyGsonMappersUtils {

    @Throws(FileNotFoundException::class)
    fun <T> mapJsonToModelFromString(fileName: String, clazz: Class<T>): T {
        return Gson().fromJson(
            fileName,
            clazz
        )
    }

    fun getJsonFromAssets(context: Context, fileName: String): String {
        val jsonString: String
        jsonString = try {
            val `is`: InputStream = context.getAssets().open(fileName)
            val size: Int = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            String(buffer)
        } catch (e: IOException) {
            e.printStackTrace()
            return ""
        }
        return jsonString
    }
}
