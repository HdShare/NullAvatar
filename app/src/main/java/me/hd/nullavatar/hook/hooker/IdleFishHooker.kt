package me.hd.nullavatar.hook.hooker

import android.content.Context
import com.highcapable.yukihookapi.hook.factory.current
import com.highcapable.yukihookapi.hook.factory.method
import me.hd.nullavatar.hook.base.BaseHook
import me.hd.nullavatar.hook.util.AvatarUtil
import java.io.File

object IdleFishHooker : BaseHook() {
    override fun onBaseHook(ctx: Context, loader: ClassLoader) {
        "com.power_media_ext.nodes.phototakernode.PhotoTakerNode".toAppClass().method {
            name = "saveToJpegFile"
        }.hook {
            after {
                instance.current {
                    val outputPath = field { name = "mOutputPath" }.string()
                    File(outputPath).outputStream().use { AvatarUtil.getOutputStream().writeTo(it) }
                }
            }
        }
    }
}
