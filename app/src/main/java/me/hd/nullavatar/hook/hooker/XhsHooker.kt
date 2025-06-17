package me.hd.nullavatar.hook.hooker

import android.content.Context
import me.hd.nullavatar.hook.base.BaseHook
import me.hd.nullavatar.hook.util.AvatarUtil
import org.luckypray.dexkit.DexKitBridge
import org.luckypray.dexkit.wrap.DexMethod
import java.io.File

object XhsHooker : BaseHook() {
    private lateinit var saveBitmapIntoFileMethod: DexMethod

    override fun onDexFind(dexkit: DexKitBridge) {
        saveBitmapIntoFileMethod = dexkit.findMethod {
            matcher {
                name("doInBackground")
                usingEqStrings(
                    "bitmapClipTask",
                    "save bitmap into file failed",
                )
            }
        }.single().toDexMethod()
    }

    override fun onBaseHook(ctx: Context, loader: ClassLoader) {
        saveBitmapIntoFileMethod.toAppMethod().hook {
            after {
                val file = result<File>()!!
                file.outputStream().use { AvatarUtil.getOutputStream().writeTo(it) }
            }
        }
    }
}
