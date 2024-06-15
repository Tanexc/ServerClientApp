package ru.tanexc.client.core.util

import android.content.ComponentName
import android.content.Context
import android.os.Build
import android.provider.Settings
import ru.tanexc.client.domain.usecase.SetStopOnResumeUseCase
import java.util.Locale

fun getDeviceName(): String {
    val manufacturer = Build.MANUFACTURER
    val model = Build.MODEL
    if (model.lowercase(Locale.ROOT).startsWith(manufacturer.lowercase(Locale.ROOT))) {
        return model.uppercase(Locale.ROOT)
    } else {
        return manufacturer.uppercase(Locale.ROOT) + " " + model
    }
}

inline fun <reified T> Context.isAccessibilityEnabled(): Boolean {
    var enabled = 0
    try {
        enabled = Settings.Secure.getInt(contentResolver, Settings.Secure.ACCESSIBILITY_ENABLED)
    } catch (_: Settings.SettingNotFoundException) {

    }
    if (enabled == 1) {
        val name = ComponentName(applicationContext, T::class.java)
        val services = Settings.Secure.getString(contentResolver, Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES)
        return services?.contains(name.flattenToString()) ?: false
    }
    return false
}