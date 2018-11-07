package com.eslimaf.coroutines.extensions

import android.util.Log
import android.widget.ImageView
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

fun ImageView.load(
    url: String
) {
    Picasso.get().load(url).into(this)
}
