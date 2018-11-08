package com.eslimaf.coroutines.extensions

import android.util.Log
import android.view.View
import android.widget.ImageView
import com.eslimaf.coroutines.R
import com.squareup.picasso.Picasso
import java.math.BigInteger
import java.security.MessageDigest

fun String.md5(): String {
    return try {
        val md5 = MessageDigest.getInstance("MD5")
        BigInteger(1, md5.digest(this.toByteArray()))
            .toString(16)
            .padStart(32, '0')
    } catch (e: Exception) {
        Log.e("String.md5", e.message)
        ""
    }
}

/**
 * ImageView extension function use Picasso to download and apply the images
 * from the url
 */
fun ImageView.load(
    url: String
) {
    Picasso.get().load(url).placeholder(R.drawable.ic_image_black_120dp).into(this)
}

/**
 * View extension function to make it easier to set visibility
 * true -> Visible
 * false -> Invisible
 * null -> Gone
 */
fun View.visible(isVisible: Boolean?) {
    isVisible?.let {
        this.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
    } ?: run {
        this.visibility = View.GONE
    }
}
