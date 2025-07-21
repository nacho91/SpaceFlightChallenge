package com.nacho.spaceflight.challenge.common.logging

import android.util.Log
import timber.log.Timber

class CrashlyticsTree : Timber.Tree() {
    override fun log(
        priority: Int,
        tag: String?,
        message: String,
        t: Throwable?
    ) {
        if (priority == Log.VERBOSE || priority == Log.DEBUG || priority == Log.INFO) return

        // Example implementation with Crashlytics
        /*
        FirebaseCrashlytics.getInstance().log(message)

        t?.let {
            FirebaseCrashlytics.getInstance().recordException(it)
        }
        */
    }
}