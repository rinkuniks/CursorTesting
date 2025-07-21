package com.nikhil.cursortesting.utils

import androidx.window.core.layout.WindowHeightSizeClass
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass

enum class DeviceConfiguration {
    MOBILE_PORTRAIT,
    MOBILE_LANDSCAPE,
    TABLET_PORTRAIT,
    TABLET_LANDSCAPE,
    DESKTOP;

    companion object {
        fun fromWindowSizeClass(windowSizeClass: WindowSizeClass): DeviceConfiguration {
            val widthClass = windowSizeClass.windowWidthSizeClass
            val heightClass = windowSizeClass.windowHeightSizeClass

            return when {
                widthClass == WindowWidthSizeClass.COMPACT &&
                        heightClass == WindowHeightSizeClass.MEDIUM -> DeviceConfiguration.MOBILE_PORTRAIT
                widthClass == WindowWidthSizeClass.COMPACT &&
                        heightClass == WindowHeightSizeClass.EXPANDED -> DeviceConfiguration.MOBILE_PORTRAIT
                widthClass == WindowWidthSizeClass.EXPANDED &&
                        heightClass == WindowHeightSizeClass.COMPACT -> DeviceConfiguration.MOBILE_LANDSCAPE
                widthClass == WindowWidthSizeClass.MEDIUM &&
                        heightClass == WindowHeightSizeClass.EXPANDED -> DeviceConfiguration.TABLET_PORTRAIT
                widthClass == WindowWidthSizeClass.EXPANDED &&
                        heightClass == WindowHeightSizeClass.MEDIUM -> DeviceConfiguration.TABLET_LANDSCAPE
                else -> DeviceConfiguration.DESKTOP
            }
        }
    }
}