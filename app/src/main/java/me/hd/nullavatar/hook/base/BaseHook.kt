package me.hd.nullavatar.hook.base

import android.content.Context
import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker
import com.highcapable.yukihookapi.hook.log.YLog
import me.hd.nullavatar.hook.data.NullAvatar
import org.luckypray.dexkit.DexKitBridge
import org.luckypray.dexkit.wrap.DexClass
import org.luckypray.dexkit.wrap.DexMethod
import kotlin.system.measureTimeMillis

abstract class BaseHook : YukiBaseHooker() {
    override fun onHook() {
        YLog.debug("loading module: ${NullAvatar.APP_NAME}(${NullAvatar.VER_NAME})")
        val costTime = measureTimeMillis {
            System.loadLibrary("dexkit")
            DexKitBridge.create(appClassLoader!!, true).use(::onDexFind)
        }
        YLog.debug("dexkit find: costTime(${costTime}ms)")
        onAppLifecycle {
            onCreate {
                withProcess(mainProcessName) {
                    onBaseHook(appContext!!, appClassLoader!!)
                }
            }
        }
    }

    protected fun String.toAppClass() = this.toClass(appClassLoader!!)
    protected fun DexClass.toAppClass() = this.getInstance(appClassLoader!!)
    protected fun DexMethod.toAppMethod() = this.getMethodInstance(appClassLoader!!)
    protected fun DexMethod.toAppConstructor() = this.getConstructorInstance(appClassLoader!!)

    open fun onDexFind(dexkit: DexKitBridge) {}

    abstract fun onBaseHook(ctx: Context, loader: ClassLoader)
}
