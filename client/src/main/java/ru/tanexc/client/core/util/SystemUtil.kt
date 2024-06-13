package ru.tanexc.client.core.util

import android.os.Build
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