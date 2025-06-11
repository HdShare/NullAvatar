package me.hd.nullavatar.hook.hooker

import android.content.Context
import com.highcapable.yukihookapi.hook.factory.method
import com.highcapable.yukihookapi.hook.type.android.BitmapClass
import com.highcapable.yukihookapi.hook.type.android.ContextClass
import me.hd.nullavatar.hook.base.BaseHook
import me.hd.nullavatar.hook.util.AvatarUtil

object AMapHooker : BaseHook() {
    override fun onBaseHook(ctx: Context, loader: ClassLoader) {
        "com.autonavi.minimap.ajx3.views.Ajx3CropPhotoView".toAppClass().method {
            name = "saveBitmapToAmapSdcard"
            param(ContextClass, BitmapClass)
        }.hook {
            before {
                args(1).set(AvatarUtil.getBitmap())
            }
        }
    }
}
