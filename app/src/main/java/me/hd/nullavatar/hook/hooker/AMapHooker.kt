package me.hd.nullavatar.hook.hooker

import android.content.Context
import android.graphics.Bitmap
import com.highcapable.kavaref.KavaRef.Companion.resolve
import com.highcapable.kavaref.extension.classOf
import me.hd.nullavatar.hook.base.BaseHook
import me.hd.nullavatar.hook.util.AvatarUtil

object AMapHooker : BaseHook() {
    override fun onBaseHook(ctx: Context, loader: ClassLoader) {
        "com.autonavi.minimap.ajx3.views.Ajx3CropPhotoView".toAppClass().resolve().apply {
            firstMethod {
                name = "saveBitmapToAmapSdcard"
                parameters(classOf<Context>(), classOf<Bitmap>())
            }.hook {
                before {
                    args(1).set(AvatarUtil.getBitmap())
                }
            }
        }
    }
}
