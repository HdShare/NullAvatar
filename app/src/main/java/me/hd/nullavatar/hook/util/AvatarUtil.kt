package me.hd.nullavatar.hook.util

import android.graphics.Bitmap
import androidx.core.graphics.createBitmap
import java.io.ByteArrayOutputStream

object AvatarUtil {
    fun getBitmap(): Bitmap {
        return createBitmap(64, 64)
    }

    fun getOutputStream(): ByteArrayOutputStream {
        return ByteArrayOutputStream().apply {
            getBitmap().compress(Bitmap.CompressFormat.PNG, 100, this)
        }
    }

    fun getByteArray(): ByteArray {
        return getOutputStream().toByteArray()
    }
}
