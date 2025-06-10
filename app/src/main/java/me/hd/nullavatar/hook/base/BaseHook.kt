package me.hd.nullavatar.hook.base

import android.app.Application
import android.content.Context
import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker
import com.highcapable.yukihookapi.hook.factory.method
import com.highcapable.yukihookapi.hook.log.YLog
import com.highcapable.yukihookapi.hook.type.android.ApplicationClass
import com.highcapable.yukihookapi.hook.type.android.InstrumentationClass
import me.hd.nullavatar.hook.data.NullAvatar
import org.luckypray.dexkit.DexKitBridge
import org.luckypray.dexkit.wrap.DexClass
import org.luckypray.dexkit.wrap.DexMethod
import kotlin.properties.Delegates
import kotlin.system.measureTimeMillis

abstract class BaseHook : YukiBaseHooker() {
    private var ctx by Delegates.notNull<Context>()
    private var clsLoader by Delegates.notNull<ClassLoader>()

    override fun onHook() {
        InstrumentationClass.apply {
            method {
                name = "callApplicationOnCreate"
                param(ApplicationClass)
            }.hook {
                after {
                    val application = args(0).cast<Application>()!!
                    ctx = application.baseContext
                    clsLoader = ctx.classLoader
                    withProcess(mainProcessName) {
                        YLog.debug("loading module: ${NullAvatar.APP_NAME}(${NullAvatar.VER_NAME})")
                        System.loadLibrary("dexkit")
                        val costTime = measureTimeMillis {
                            DexKitBridge.create(clsLoader, true).use(::onDexFind)
                        }
                        YLog.debug("dexkit find: costTime(${costTime}ms)")
                        onBaseHook(ctx, clsLoader)
                    }
                }
            }
        }
    }

    protected fun String.toAppClass() = this.toClass(clsLoader)
    protected fun DexClass.toAppClass() = this.getInstance(clsLoader)
    protected fun DexMethod.toAppMethod() = this.getMethodInstance(clsLoader)
    protected fun DexMethod.toAppConstructor() = this.getConstructorInstance(clsLoader)

    open fun onDexFind(dexkit: DexKitBridge) {}

    abstract fun onBaseHook(ctx: Context, loader: ClassLoader)
}
