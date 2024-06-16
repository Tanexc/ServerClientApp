package ru.tanexc.client.presentation.service.util

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.graphics.Path

fun AccessibilityService.gesture(
    dx: Double,
    dy: Double,
    duration: Long,
) {
    val metrics = resources.displayMetrics
    val x: Float = metrics.widthPixels.toFloat() / 2
    val y: Float = metrics.heightPixels.toFloat() / 2

    val path = Path()
    path.moveTo(x, y)
    path.lineTo(x + x * dx.toFloat(), y + y * dy.toFloat())
    val gestureBuilder = GestureDescription.Builder()
    gestureBuilder.addStroke(GestureDescription.StrokeDescription(path, 0, duration))
    dispatchGesture(gestureBuilder.build(), null, null)
}
