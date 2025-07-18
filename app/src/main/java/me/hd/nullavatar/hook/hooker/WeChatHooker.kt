package me.hd.nullavatar.hook.hooker

import android.content.Context
import com.highcapable.kavaref.KavaRef.Companion.asResolver
import me.hd.nullavatar.hook.base.BaseHook
import me.hd.nullavatar.hook.util.AvatarUtil
import org.luckypray.dexkit.DexKitBridge
import org.luckypray.dexkit.wrap.DexMethod
import java.io.File

object WeChatHooker : BaseHook() {
    private lateinit var getClipBitmapMethod: DexMethod

    override fun onDexFind(dexkit: DexKitBridge) {
        getClipBitmapMethod = dexkit.findMethod {
            matcher {
                usingEqStrings(
                    "MediaTailor",
                    "Rect width or height contains zero. contentRect: ",
                )
            }
        }.single().toDexMethod()
    }

    override fun onBaseHook(ctx: Context, loader: ClassLoader) {
        getClipBitmapMethod.toAppMethod().hook {
            after {
                val path = result!!.asResolver().firstField { type = String::class }.get<String>()!!
                File(path).outputStream().use { AvatarUtil.getOutputStream().writeTo(it) }
            }
        }
    }
}
