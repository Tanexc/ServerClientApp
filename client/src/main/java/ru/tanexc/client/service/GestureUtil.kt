package ru.tanexc.client.service

import android.accessibilityservice.GestureDescription
import android.content.Context
import android.graphics.Path
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.annotation.RequiresApi
import kotlin.time.Duration

fun Context.gesture(
    dx: Double,
    dy: Double,
    duration: Long
) {
    val metrics = DisplayMetrics()
    val x: Float = metrics.widthPixels.toFloat() / 2
    val y: Float = metrics.heightPixels.toFloat() / 2

    val path = Path()
    path.moveTo(x, y)
    path.lineTo(x + dx.toFloat(), y + dy.toFloat())

    val gestureBuilder = GestureDescription.Builder()
    gestureBuilder.addStroke(GestureDescription.StrokeDescription(path, 0, 100))

}