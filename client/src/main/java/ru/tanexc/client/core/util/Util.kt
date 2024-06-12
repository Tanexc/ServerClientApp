package ru.tanexc.client.core.util

import android.os.Build
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
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