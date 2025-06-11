package me.hd.nullavatar.hook.hooker

import android.content.Context
import com.highcapable.yukihookapi.hook.factory.method
import com.highcapable.yukihookapi.hook.type.android.BitmapClass
import me.hd.nullavatar.hook.base.BaseHook
import me.hd.nullavatar.hook.util.AvatarUtil

object EasyBikeHooker : BaseHook() {
    override fun onBaseHook(ctx: Context, loader: ClassLoader) {
        "com.cheyaoshi.cropimage.CropImageActivity".toAppClass().method {
            name = "saveOutput"
            param(BitmapClass)
        }.hook {
            before {
                args(0).set(AvatarUtil.getBitmap())
            }
        }
    }
}
