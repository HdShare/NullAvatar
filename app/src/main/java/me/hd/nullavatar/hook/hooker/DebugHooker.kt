package me.hd.nullavatar.hook.hooker

import android.content.Context
import android.graphics.Bitmap
import com.highcapable.yukihookapi.hook.factory.method
import com.highcapable.yukihookapi.hook.type.android.BitmapClass
import me.hd.nullavatar.hook.base.BaseHook
import me.hd.nullavatar.hook.util.printStackTrace

object DebugHooker : BaseHook() {
    override fun onBaseHook(ctx: Context, loader: ClassLoader) {
        BitmapClass.apply {
            method {
                name = "compress"
            }.hook {
                after {
                    printStackTrace()
                    args(0).set(Bitmap.CompressFormat.PNG)
                }
            }
        }
    }
}
