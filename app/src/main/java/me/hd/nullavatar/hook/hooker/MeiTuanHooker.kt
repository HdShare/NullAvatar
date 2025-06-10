package me.hd.nullavatar.hook.hooker

import android.content.Context
import com.highcapable.yukihookapi.hook.type.java.ByteArrayType
import me.hd.nullavatar.hook.base.BaseHook
import me.hd.nullavatar.hook.util.AvatarUtil
import org.luckypray.dexkit.DexKitBridge
import org.luckypray.dexkit.wrap.DexMethod

object MeiTuanHooker : BaseHook() {
    private lateinit var updateUserAvatarPictureMethod: DexMethod

    override fun onDexFind(dexkit: DexKitBridge) {
        updateUserAvatarPictureMethod = dexkit.findMethod {
            matcher {
                paramTypes(null, null, ByteArrayType)
                usingEqStrings(
                    "NetUtils.updateUserAvatarPicture.new",
                    "user is login",
                    "true"
                )
            }
        }.single().toDexMethod()
    }

    override fun onBaseHook(ctx: Context, loader: ClassLoader) {
        updateUserAvatarPictureMethod.toAppMethod().hook {
            before {
                args(2).set(AvatarUtil.getByteArray())
            }
        }
    }
}
