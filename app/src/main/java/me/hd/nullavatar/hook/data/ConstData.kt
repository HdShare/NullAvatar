package me.hd.nullavatar.hook.data

import me.hd.nullavatar.BuildConfig

object BuildWrapper {
    val DEBUG = BuildConfig.DEBUG
    const val APPLICATION_ID = BuildConfig.APPLICATION_ID
    const val VERSION_CODE = BuildConfig.VERSION_CODE
    const val VERSION_NAME = BuildConfig.VERSION_NAME
    const val APP_NAME = BuildConfig.APP_NAME
}

object NullAvatar {
    const val APP_ID = BuildWrapper.APPLICATION_ID
    const val APP_NAME = BuildWrapper.APP_NAME
    const val VER_CODE = BuildWrapper.VERSION_CODE
    const val VER_NAME = BuildWrapper.VERSION_NAME
    const val HOOK_TAG = APP_NAME
}
