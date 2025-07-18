package me.hd.nullavatar.hook.hooker

import android.content.Context
import android.graphics.Bitmap
import com.highcapable.kavaref.KavaRef.Companion.resolve
import com.highcapable.kavaref.extension.classOf
import me.hd.nullavatar.hook.base.BaseHook
import me.hd.nullavatar.hook.util.AvatarUtil

object EasyBikeHooker : BaseHook() {
    override fun onBaseHook(ctx: Context, loader: ClassLoader) {
        "com.cheyaoshi.cropimage.CropImageActivity".toAppClass().resolve().apply {
            firstMethod {
                name = "saveOutput"
                parameters(classOf<Bitmap>())
            }.hook {
                before {
                    args(0).set(AvatarUtil.getBitmap())
                }
            }
        }
    }
}
