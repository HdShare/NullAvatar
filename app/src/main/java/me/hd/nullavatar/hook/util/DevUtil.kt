@file:Suppress("unused")

package me.hd.nullavatar.hook.util

import com.highcapable.yukihookapi.hook.log.YLog

fun printStackTrace() {
    val stackTrace = Throwable().stackTrace
    val stackTraceStr = stackTrace.joinToString("\n") { element ->
        "at ${element.className}.${element.methodName}(${element.fileName}:${element.lineNumber})"
    }
    YLog.debug("StackTrace\n$stackTraceStr")
}
