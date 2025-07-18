package me.hd.nullavatar.hook.hooker

import android.content.Context
import android.graphics.Bitmap
import com.highcapable.kavaref.extension.classOf
import me.hd.nullavatar.hook.base.BaseHook
import me.hd.nullavatar.hook.util.AvatarUtil
import org.luckypray.dexkit.DexKitBridge
import org.luckypray.dexkit.wrap.DexMethod

object AlipayHooker : BaseHook() {
    private lateinit var loadCompressedByteMethod: DexMethod

    override fun onDexFind(dexkit: DexKitBridge) {
        loadCompressedByteMethod = dexkit.findMethod {
            matcher {
                declaredClass("com.alipay.mobile.security.photo.ui.SelectPhotoActivity")
                returnType(classOf<ByteArray>())
                paramTypes(classOf<Bitmap>(), classOf<Long>())
                usingEqStrings(
                    "EditAvatarActivity",
                    "小图片，不压缩",
                    "调用CompressImageBitmapDefaultNoChange接口出现异常，做降级处理（定Q压缩）",
                )
            }
        }.single().toDexMethod()
    }

    override fun onBaseHook(ctx: Context, loader: ClassLoader) {
        loadCompressedByteMethod.toAppMethod().hook {
            before {
                result = AvatarUtil.getByteArray()
            }
        }
    }
}
