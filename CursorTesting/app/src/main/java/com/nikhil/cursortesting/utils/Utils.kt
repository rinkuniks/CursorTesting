package com.nikhil.cursortesting.utils

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager
import kotlin.math.sqrt

/**
 * Utility class for calculating responsive text sizes based on device dimensions and density
 * All values are dynamically calculated from the current device
 */
object Utils {

    /**
     * Calculate responsive text size based on current device dimensions and density
     * @param context Android context
     * @param baseSize Base text size in sp (will be scaled based on device characteristics)
     * @return Calculated text size in sp
     */
    fun calculateResponsiveTextSize(
        context: Context,
        baseSize: Float
    ): Float {
        val displayMetrics = DisplayMetrics()
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        val screenWidth = displayMetrics.widthPixels / displayMetrics.density
        val screenHeight = displayMetrics.heightPixels / displayMetrics.density
        val currentDensity = displayMetrics.density

        // Calculate device characteristics
        val screenArea = screenWidth * screenHeight
        val screenDiagonal = sqrt(screenWidth * screenWidth + screenHeight * screenHeight)
        
        // Calculate scale factors based on device characteristics
        val areaScale = calculateAreaScale(screenArea)
        val diagonalScale = calculateDiagonalScale(screenDiagonal)
        val densityScale = calculateDensityScale(currentDensity)
        
        // Combine all factors with weighted approach
        val finalScale = (areaScale * 0.4f + diagonalScale * 0.3f + densityScale * 0.3f)
        
        // Apply reasonable bounds to prevent extreme scaling
        val boundedScale = finalScale.coerceIn(0.6f, 1.8f)
        
        return baseSize * boundedScale
    }

    /**
     * Calculate text size based on screen width only
     * @param context Android context
     * @param baseSize Base text size in sp
     * @return Calculated text size in sp
     */
    fun calculateTextSizeByWidth(
        context: Context,
        baseSize: Float
    ): Float {
        val displayMetrics = DisplayMetrics()
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        val screenWidth = displayMetrics.widthPixels / displayMetrics.density
        val scale = calculateWidthScale(screenWidth)
        
        return baseSize * scale
    }

    /**
     * Calculate text size based on screen height only
     * @param context Android context
     * @param baseSize Base text size in sp
     * @return Calculated text size in sp
     */
    fun calculateTextSizeByHeight(
        context: Context,
        baseSize: Float
    ): Float {
        val displayMetrics = DisplayMetrics()
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        val screenHeight = displayMetrics.heightPixels / displayMetrics.density
        val scale = calculateHeightScale(screenHeight)
        
        return baseSize * scale
    }

    /**
     * Calculate text size based on screen diagonal
     * @param context Android context
     * @param baseSize Base text size in sp
     * @return Calculated text size in sp
     */
    fun calculateTextSizeByDiagonal(
        context: Context,
        baseSize: Float
    ): Float {
        val displayMetrics = DisplayMetrics()
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        val widthInches = displayMetrics.widthPixels / displayMetrics.xdpi
        val heightInches = displayMetrics.heightPixels / displayMetrics.ydpi
        val diagonalInches = sqrt(widthInches * widthInches + heightInches * heightInches)
        
        val scale = calculateDiagonalScale(diagonalInches * 160) // Convert to dp equivalent
        
        return baseSize * scale
    }

    /**
     * Calculate scale based on screen area
     */
    private fun calculateAreaScale(screenArea: Float): Float {
        // Reference area for a typical phone (360dp x 640dp = 230,400 dp²)
        val referenceArea = 360f * 640f
        
        return when {
            screenArea < 150000f -> 0.8f  // Small phones
            screenArea < 200000f -> 0.9f  // Medium phones
            screenArea < 300000f -> 1.0f  // Large phones
            screenArea < 500000f -> 1.1f  // Small tablets
            screenArea < 800000f -> 1.2f  // Medium tablets
            else -> 1.3f                  // Large tablets
        }
    }

    /**
     * Calculate scale based on screen diagonal
     */
    private fun calculateDiagonalScale(screenDiagonal: Float): Float {
        return when {
            screenDiagonal < 400f -> 0.8f  // Small phones
            screenDiagonal < 500f -> 0.9f  // Medium phones
            screenDiagonal < 600f -> 1.0f  // Large phones
            screenDiagonal < 700f -> 1.1f  // Small tablets
            screenDiagonal < 800f -> 1.2f  // Medium tablets
            else -> 1.3f                   // Large tablets
        }
    }

    /**
     * Calculate scale based on screen density
     */
    private fun calculateDensityScale(density: Float): Float {
        return when {
            density < 1.5f -> 0.9f  // Low density
            density < 2.0f -> 1.0f  // Medium density
            density < 2.5f -> 1.05f // High density
            density < 3.0f -> 1.1f  // Very high density
            else -> 1.15f           // Extra high density
        }
    }

    /**
     * Calculate scale based on screen width
     */
    private fun calculateWidthScale(screenWidth: Float): Float {
        return when {
            screenWidth < 300f -> 0.8f  // Very narrow screens
            screenWidth < 360f -> 0.9f  // Narrow screens
            screenWidth < 400f -> 1.0f  // Standard screens
            screenWidth < 500f -> 1.1f  // Wide screens
            screenWidth < 600f -> 1.2f  // Very wide screens
            else -> 1.3f               // Tablet screens
        }
    }

    /**
     * Calculate scale based on screen height
     */
    private fun calculateHeightScale(screenHeight: Float): Float {
        return when {
            screenHeight < 500f -> 0.8f  // Short screens
            screenHeight < 600f -> 0.9f  // Medium height screens
            screenHeight < 700f -> 1.0f  // Standard height screens
            screenHeight < 800f -> 1.1f  // Tall screens
            screenHeight < 1000f -> 1.2f // Very tall screens
            else -> 1.3f                // Tablet height screens
        }
    }

    /**
     * Get device screen information for debugging
     * @param context Android context
     * @return String containing device screen information
     */
    fun getDeviceInfo(context: Context): String {
        val displayMetrics = DisplayMetrics()
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        val screenWidth = displayMetrics.widthPixels / displayMetrics.density
        val screenHeight = displayMetrics.heightPixels / displayMetrics.density
        val screenArea = screenWidth * screenHeight
        val screenDiagonal = sqrt(screenWidth * screenWidth + screenHeight * screenHeight)
        val widthInches = displayMetrics.widthPixels / displayMetrics.xdpi
        val heightInches = displayMetrics.heightPixels / displayMetrics.ydpi
        val diagonalInches = sqrt(widthInches * widthInches + heightInches * heightInches)

        return """
            Screen Dimensions:
            - Width: ${displayMetrics.widthPixels}px (${"%.1f".format(screenWidth)}dp)
            - Height: ${displayMetrics.heightPixels}px (${"%.1f".format(screenHeight)}dp)
            - Area: ${"%.0f".format(screenArea)} dp²
            - Diagonal: ${"%.1f".format(screenDiagonal)} dp (${"%.1f".format(diagonalInches)} inches)
            
            Density Information:
            - Density: ${displayMetrics.density}
            - Density DPI: ${displayMetrics.densityDpi}
            - X DPI: ${"%.1f".format(displayMetrics.xdpi)}
            - Y DPI: ${"%.1f".format(displayMetrics.ydpi)}
            
            Calculated Scales:
            - Area Scale: ${"%.2f".format(calculateAreaScale(screenArea))}
            - Diagonal Scale: ${"%.2f".format(calculateDiagonalScale(screenDiagonal))}
            - Density Scale: ${"%.2f".format(calculateDensityScale(displayMetrics.density))}
            - Width Scale: ${"%.2f".format(calculateWidthScale(screenWidth))}
            - Height Scale: ${"%.2f".format(calculateHeightScale(screenHeight))}
        """.trimIndent()
    }

    /**
     * Get device category for debugging
     * @param context Android context
     * @return String describing the device category
     */
    fun getDeviceCategory(context: Context): String {
        val displayMetrics = DisplayMetrics()
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        val screenWidth = displayMetrics.widthPixels / displayMetrics.density
        val screenHeight = displayMetrics.heightPixels / displayMetrics.density
        val screenArea = screenWidth * screenHeight
        val screenDiagonal = sqrt(screenWidth * screenWidth + screenHeight * screenHeight)

        return when {
            screenArea < 150000f -> "Small Phone"
            screenArea < 200000f -> "Medium Phone"
            screenArea < 300000f -> "Large Phone"
            screenArea < 500000f -> "Small Tablet"
            screenArea < 800000f -> "Medium Tablet"
            else -> "Large Tablet"
        }
    }

    /**
     * Predefined text sizes for common UI elements
     */
    object TextSizes {
        const val HEADLINE_LARGE = 32f
        const val HEADLINE_MEDIUM = 28f
        const val HEADLINE_SMALL = 24f
        const val TITLE_LARGE = 22f
        const val TITLE_MEDIUM = 16f
        const val TITLE_SMALL = 14f
        const val BODY_LARGE = 16f
        const val BODY_MEDIUM = 14f
        const val BODY_SMALL = 12f
        const val LABEL_LARGE = 14f
        const val LABEL_MEDIUM = 12f
        const val LABEL_SMALL = 11f
    }
} 