package ru.tanexc.client.service.util

import android.content.Context
import android.content.Intent
import android.net.Uri

fun Context.launchActivity(
    pack: String,
    data: Uri? = null,
    vararg flags: Int,
) {
    val intent = Intent(Intent.ACTION_VIEW)

    flags.forEach { flag ->
        intent.setFlags(flag)
    }
    if (data != null) intent.data = data
    intent.`package` = pack

    this.startActivity(intent)
}
