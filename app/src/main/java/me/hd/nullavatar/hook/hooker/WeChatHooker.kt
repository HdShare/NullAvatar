package me.hd.nullavatar.hook.hooker

import android.content.Context
import com.highcapable.yukihookapi.hook.factory.current
import com.highcapable.yukihookapi.hook.type.java.StringClass
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
                result!!.current {
                    val path = field { type = StringClass }.string()
                    File(path).outputStream().use { AvatarUtil.getOutputStream().writeTo(it) }
                }
            }
        }
    }
}
