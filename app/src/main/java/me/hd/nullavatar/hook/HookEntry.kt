package me.hd.nullavatar.hook

import com.highcapable.yukihookapi.YukiHookAPI.configs
import com.highcapable.yukihookapi.YukiHookAPI.encase
import com.highcapable.yukihookapi.annotation.xposed.InjectYukiHookWithXposed
import com.highcapable.yukihookapi.hook.xposed.proxy.IYukiHookXposedInit
import me.hd.nullavatar.hook.data.NullAvatar
import me.hd.nullavatar.hook.hooker.AMapHooker
import me.hd.nullavatar.hook.hooker.AlipayHooker
import me.hd.nullavatar.hook.hooker.DebugHooker
import me.hd.nullavatar.hook.hooker.EleHooker
import me.hd.nullavatar.hook.hooker.MeiTuanHooker

@InjectYukiHookWithXposed(entryClassName = "Entry")
object HookEntry : IYukiHookXposedInit {
    override fun onInit() = configs {
        debugLog { tag = NullAvatar.HOOK_TAG }
        isDebug = false
        isEnableDataChannel = false
    }

    override fun onHook() = encase {
        loadApp("me.hd.debug", DebugHooker)
        loadApp("com.eg.android.AlipayGphone", AlipayHooker)
        loadApp("me.ele", EleHooker)
        loadApp("com.sankuai.meituan", MeiTuanHooker)
        loadApp("com.autonavi.minimap", AMapHooker)
    }
}
