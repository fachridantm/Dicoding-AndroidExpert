package com.dicoding.githubapp.core.utils

import android.content.Context
import android.content.Intent
import android.os.Build.VERSION.SDK_INT
import android.os.Parcelable
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestOptions
import com.dicoding.githubapp.core.R
import org.json.JSONObject
import retrofit2.HttpException

fun String.showMessage(context: Context) {
    Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
}

fun HttpException.getErrorMessage(): String? {
    val response = this.response()?.errorBody()?.string()
    if (response.isNullOrEmpty()) return "Something went wrong"
    return try {
        val jsonObject = JSONObject(response)
        jsonObject.getString("message")
    } catch (e: Exception) {
        e.printStackTrace()
        e.message.toString()
    }
}

fun ImageView.loadUserImage(url: String) {
    try {
        Glide.with(this.context)
            .load(url)
            .apply(
                RequestOptions().override(100, 100)
                    .circleCrop()
                    .placeholder(R.drawable.ic_loading)
                    .error(R.drawable.ic_error)
            )
            .into(this)
    } catch (e: GlideException) {
        e.logRootCauses("GlideException")
    }
}

fun Int.convertToShortNumber(): String {
    return when {
        this < 1000 -> this.toString()
        this < 1000000 -> "${this / 1000}.${(this % 1000) / 100}K"
        else -> "${this / 1000000}.${(this % 1000000) / 100000}M"
    }
}

inline fun <reified T : Parcelable> Intent.parcelable(key: String): T? = when {
    SDK_INT >= 33 -> @Suppress("DEPRECATION") getParcelableExtra(key)
    else -> @Suppress("DEPRECATION") getParcelableExtra(key) as? T
}

inline fun <reified T : Parcelable> Intent.parcelableArrayList(key: String): ArrayList<T>? {
    return when {
        SDK_INT >= 33 -> getParcelableArrayListExtra(key, T::class.java)
        else -> @Suppress("DEPRECATION") getParcelableArrayListExtra(key)
    }
}
