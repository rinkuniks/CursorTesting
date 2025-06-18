package com.nikhil.cursortesting.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

/**
 * Compose extensions for responsive text sizing
 * All calculations are based on the current device's characteristics
 */
object ResponsiveText {

    /**
     * Get responsive text size for headline large
     */
    @Composable
    fun headlineLarge(): Float {
        val context = LocalContext.current
        return Utils.calculateResponsiveTextSize(
            context = context,
            baseSize = Utils.TextSizes.HEADLINE_LARGE
        )
    }

    /**
     * Get responsive text size for headline medium
     */
    @Composable
    fun headlineMedium(): Float {
        val context = LocalContext.current
        return Utils.calculateResponsiveTextSize(
            context = context,
            baseSize = Utils.TextSizes.HEADLINE_MEDIUM
        )
    }

    /**
     * Get responsive text size for headline small
     */
    @Composable
    fun headlineSmall(): Float {
        val context = LocalContext.current
        return Utils.calculateResponsiveTextSize(
            context = context,
            baseSize = Utils.TextSizes.HEADLINE_SMALL
        )
    }

    /**
     * Get responsive text size for title large
     */
    @Composable
    fun titleLarge(): Float {
        val context = LocalContext.current
        return Utils.calculateResponsiveTextSize(
            context = context,
            baseSize = Utils.TextSizes.TITLE_LARGE
        )
    }

    /**
     * Get responsive text size for title medium
     */
    @Composable
    fun titleMedium(): Float {
        val context = LocalContext.current
        return Utils.calculateResponsiveTextSize(
            context = context,
            baseSize = Utils.TextSizes.TITLE_MEDIUM
        )
    }

    /**
     * Get responsive text size for title small
     */
    @Composable
    fun titleSmall(): Float {
        val context = LocalContext.current
        return Utils.calculateResponsiveTextSize(
            context = context,
            baseSize = Utils.TextSizes.TITLE_SMALL
        )
    }

    /**
     * Get responsive text size for body large
     */
    @Composable
    fun bodyLarge(): Float {
        val context = LocalContext.current
        return Utils.calculateResponsiveTextSize(
            context = context,
            baseSize = Utils.TextSizes.BODY_LARGE
        )
    }

    /**
     * Get responsive text size for body medium
     */
    @Composable
    fun bodyMedium(): Float {
        val context = LocalContext.current
        return Utils.calculateResponsiveTextSize(
            context = context,
            baseSize = Utils.TextSizes.BODY_MEDIUM
        )
    }

    /**
     * Get responsive text size for body small
     */
    @Composable
    fun bodySmall(): Float {
        val context = LocalContext.current
        return Utils.calculateResponsiveTextSize(
            context = context,
            baseSize = Utils.TextSizes.BODY_SMALL
        )
    }

    /**
     * Get responsive text size for label large
     */
    @Composable
    fun labelLarge(): Float {
        val context = LocalContext.current
        return Utils.calculateResponsiveTextSize(
            context = context,
            baseSize = Utils.TextSizes.LABEL_LARGE
        )
    }

    /**
     * Get responsive text size for label medium
     */
    @Composable
    fun labelMedium(): Float {
        val context = LocalContext.current
        return Utils.calculateResponsiveTextSize(
            context = context,
            baseSize = Utils.TextSizes.LABEL_MEDIUM
        )
    }

    /**
     * Get responsive text size for label small
     */
    @Composable
    fun labelSmall(): Float {
        val context = LocalContext.current
        return Utils.calculateResponsiveTextSize(
            context = context,
            baseSize = Utils.TextSizes.LABEL_SMALL
        )
    }

    /**
     * Get custom responsive text size based on device characteristics
     */
    @Composable
    fun custom(baseSize: Float): Float {
        val context = LocalContext.current
        return Utils.calculateResponsiveTextSize(
            context = context,
            baseSize = baseSize
        )
    }

    /**
     * Get responsive text size by width only
     */
    @Composable
    fun byWidth(baseSize: Float): Float {
        val context = LocalContext.current
        return Utils.calculateTextSizeByWidth(
            context = context,
            baseSize = baseSize
        )
    }

    /**
     * Get responsive text size by height only
     */
    @Composable
    fun byHeight(baseSize: Float): Float {
        val context = LocalContext.current
        return Utils.calculateTextSizeByHeight(
            context = context,
            baseSize = baseSize
        )
    }

    /**
     * Get responsive text size by diagonal
     */
    @Composable
    fun byDiagonal(baseSize: Float): Float {
        val context = LocalContext.current
        return Utils.calculateTextSizeByDiagonal(
            context = context,
            baseSize = baseSize
        )
    }

    /**
     * Get device category information
     */
    @Composable
    fun getDeviceCategory(): String {
        val context = LocalContext.current
        return Utils.getDeviceCategory(context)
    }
} 