package me.hd.nullavatar.hook.hooker

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.highcapable.yukihookapi.hook.factory.method
import me.hd.nullavatar.hook.base.BaseHook
import me.hd.nullavatar.hook.util.AvatarUtil
import java.io.File

object EleHooker : BaseHook() {
    override fun onBaseHook(ctx: Context, loader: ClassLoader) {
        "me.ele.account.widget.PhotoChooserDialog".toAppClass().method {
            name = "onFragmentResult"
        }.hook {
            before {
                val requestCode = args(0).int()
                val resultCode = args(1).int()
                val intent = args(2).cast<Intent>()!!
                if (resultCode == Activity.RESULT_OK && requestCode == 819) {
                    val file = File(ctx.cacheDir, "ecamera/IMG_CACHE.png").apply { parentFile?.mkdirs() }
                    file.outputStream().use { AvatarUtil.getOutputStream().writeTo(it) }
                    intent.data = Uri.fromFile(file)
                }
            }
        }
    }
}
