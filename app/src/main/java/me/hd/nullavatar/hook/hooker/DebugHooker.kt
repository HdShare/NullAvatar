package me.hd.nullavatar.hook.hooker

import android.content.Context
import android.graphics.Bitmap
import com.highcapable.kavaref.KavaRef.Companion.resolve
import me.hd.nullavatar.hook.base.BaseHook
import me.hd.nullavatar.hook.util.printStackTrace

object DebugHooker : BaseHook() {
    override fun onBaseHook(ctx: Context, loader: ClassLoader) {
        "android.graphics.Bitmap".toAppClass().resolve().apply {
            firstMethod {
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
