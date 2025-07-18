package me.hd.nullavatar.hook.hooker

import android.content.Context
import com.highcapable.kavaref.KavaRef.Companion.asResolver
import com.highcapable.kavaref.KavaRef.Companion.resolve
import me.hd.nullavatar.hook.base.BaseHook
import me.hd.nullavatar.hook.util.AvatarUtil
import java.io.File

object IdleFishHooker : BaseHook() {
    override fun onBaseHook(ctx: Context, loader: ClassLoader) {
        "com.power_media_ext.nodes.phototakernode.PhotoTakerNode".toAppClass().resolve().apply {
            firstMethod {
                name = "saveToJpegFile"
            }.hook {
                after {
                    val outputPath = instance.asResolver().firstField { name = "mOutputPath" }.get<String>()!!
                    File(outputPath).outputStream().use { AvatarUtil.getOutputStream().writeTo(it) }
                }
            }
        }
    }
}
