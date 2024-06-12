package ru.tanexc.client.service

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.content.Context
import android.graphics.Path
import android.os.Build
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowManager
import androidx.annotation.RequiresApi
import com.fasterxml.jackson.annotation.JsonProperty.Access
import kotlin.time.Duration

fun AccessibilityService.gesture(
    dx: Double,
    dy: Double,
    duration: Long
) {
    val metrics = resources.displayMetrics
    val x: Float = metrics.widthPixels.toFloat() / 2
    val y: Float = metrics.heightPixels.toFloat() / 2

    val path = Path()
    path.moveTo(x, y)
    path.lineTo(x + x * dx.toFloat(), y + y * dy.toFloat())
    Log.i("cum", "gesture")
    val gestureBuilder = GestureDescription.Builder()
    gestureBuilder.addStroke(GestureDescription.StrokeDescription(path, 0, duration))
    dispatchGesture(gestureBuilder.build(), null, null)
}