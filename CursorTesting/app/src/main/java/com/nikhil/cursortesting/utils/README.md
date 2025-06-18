# Dynamic Text Size Utilities

This utility provides functions to calculate responsive text sizes based on the **actual device dimensions and density**, ensuring consistent text appearance across different Android devices. All calculations are performed dynamically based on the current device's characteristics.

## Features

- **Fully Dynamic**: All calculations based on actual device characteristics
- **Multi-factor scaling**: Considers screen area, diagonal, and density
- **Device categorization**: Automatically detects device type (phone/tablet)
- **Bounded scaling**: Prevents text from becoming too small or too large
- **Multiple scaling methods**: Width-based, height-based, diagonal-based, and combined
- **Predefined text sizes**: Material Design text size constants
- **Compose integration**: Easy-to-use Compose extensions

## How It Works

### Dynamic Device Analysis

The utility analyzes your device's actual characteristics:

1. **Screen Area**: Calculates total screen area in dp²
2. **Screen Diagonal**: Calculates screen diagonal in dp
3. **Screen Density**: Analyzes device density and DPI
4. **Device Categorization**: Automatically categorizes as Small/Medium/Large Phone or Tablet

### Scaling Algorithm

1. **Area Scaling**: Based on total screen area
   - Small phones (< 150k dp²): 0.8x scale
   - Medium phones (< 200k dp²): 0.9x scale
   - Large phones (< 300k dp²): 1.0x scale
   - Small tablets (< 500k dp²): 1.1x scale
   - Medium tablets (< 800k dp²): 1.2x scale
   - Large tablets (≥ 800k dp²): 1.3x scale

2. **Diagonal Scaling**: Based on screen diagonal
   - Small phones (< 400dp): 0.8x scale
   - Medium phones (< 500dp): 0.9x scale
   - Large phones (< 600dp): 1.0x scale
   - Small tablets (< 700dp): 1.1x scale
   - Medium tablets (< 800dp): 1.2x scale
   - Large tablets (≥ 800dp): 1.3x scale

3. **Density Scaling**: Based on screen density
   - Low density (< 1.5): 0.9x scale
   - Medium density (< 2.0): 1.0x scale
   - High density (< 2.5): 1.05x scale
   - Very high density (< 3.0): 1.1x scale
   - Extra high density (≥ 3.0): 1.15x scale

4. **Combined Scaling**: Weighted combination
   - 40% area scaling + 30% diagonal scaling + 30% density scaling
   - Bounded between 0.6x and 1.8x

## Usage

### Basic Usage in Compose

```kotlin
import com.nikhil.cursortesting.utils.ResponsiveText

@Composable
fun MyScreen() {
    Text(
        text = "Hello World",
        fontSize = ResponsiveText.headlineLarge().sp,
        fontWeight = FontWeight.Bold
    )
    
    Text(
        text = "Body text",
        fontSize = ResponsiveText.bodyMedium().sp
    )
    
    // Custom size
    Text(
        text = "Custom size",
        fontSize = ResponsiveText.custom(18f).sp
    )
    
    // Get device category
    Text(
        text = "Device: ${ResponsiveText.getDeviceCategory()}",
        fontSize = ResponsiveText.bodySmall().sp
    )
}
```

### Available Predefined Sizes

```kotlin
// Headlines
ResponsiveText.headlineLarge()    // 32sp base
ResponsiveText.headlineMedium()   // 28sp base
ResponsiveText.headlineSmall()    // 24sp base

// Titles
ResponsiveText.titleLarge()       // 22sp base
ResponsiveText.titleMedium()      // 16sp base
ResponsiveText.titleSmall()       // 14sp base

// Body text
ResponsiveText.bodyLarge()        // 16sp base
ResponsiveText.bodyMedium()       // 14sp base
ResponsiveText.bodySmall()        // 12sp base

// Labels
ResponsiveText.labelLarge()       // 14sp base
ResponsiveText.labelMedium()      // 12sp base
ResponsiveText.labelSmall()       // 11sp base
```

### Different Scaling Methods

```kotlin
// Combined scaling (recommended)
ResponsiveText.custom(16f)

// Width-based scaling only
ResponsiveText.byWidth(16f)

// Height-based scaling only
ResponsiveText.byHeight(16f)

// Diagonal-based scaling
ResponsiveText.byDiagonal(16f)
```

### Direct Utility Usage

```kotlin
import com.nikhil.cursortesting.utils.TextSizeUtils

val context = LocalContext.current

// Calculate dynamic text size
val textSize = TextSizeUtils.calculateResponsiveTextSize(
    context = context,
    baseSize = 16f
)

// Get detailed device information
val deviceInfo = TextSizeUtils.getDeviceInfo(context)
println(deviceInfo)

// Get device category
val deviceCategory = TextSizeUtils.getDeviceCategory(context)
println("Device: $deviceCategory")
```

## Device Categories

The utility automatically categorizes devices:

- **Small Phone**: Screen area < 150,000 dp²
- **Medium Phone**: Screen area 150,000 - 200,000 dp²
- **Large Phone**: Screen area 200,000 - 300,000 dp²
- **Small Tablet**: Screen area 300,000 - 500,000 dp²
- **Medium Tablet**: Screen area 500,000 - 800,000 dp²
- **Large Tablet**: Screen area ≥ 800,000 dp²

## Demo

Run the app and navigate to the "Text Size Demo" screen to see:
- Device category detection
- All predefined text sizes
- Custom text sizes
- Different scaling methods
- Comparison between fixed and dynamic text sizes
- Detailed device information including calculated scales

## Example Output

```
Device: Large Phone

Screen Dimensions:
- Width: 1080px (360.0dp)
- Height: 1920px (640.0dp)
- Area: 230400 dp²
- Diagonal: 734.8 dp (4.7 inches)

Density Information:
- Density: 3.0
- Density DPI: 480
- X DPI: 480.0
- Y DPI: 480.0

Calculated Scales:
- Area Scale: 1.00
- Diagonal Scale: 1.00
- Density Scale: 1.10
- Width Scale: 1.00
- Height Scale: 1.00
```

## Best Practices

1. **Use predefined sizes** for consistency with Material Design
2. **Test on multiple devices** to ensure proper scaling
3. **Use combined scaling** for best results across different screen sizes
4. **Consider accessibility** - ensure text remains readable on all devices
5. **Monitor device categories** to understand how your app scales
6. **Use the demo screen** to test on different devices

## Example Implementation

```kotlin
@Composable
fun ResponsiveCard() {
    Card(
        modifier = Modifier.padding(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Card Title",
                fontSize = ResponsiveText.titleLarge().sp,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "This is the card content with dynamic text sizing.",
                fontSize = ResponsiveText.bodyMedium().sp
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Device: ${ResponsiveText.getDeviceCategory()}",
                fontSize = ResponsiveText.labelSmall().sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Button(
                onClick = { /* action */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Action Button",
                    fontSize = ResponsiveText.labelLarge().sp
                )
            }
        }
    }
}
```

## Benefits

- **True Responsiveness**: Text sizes adapt to actual device characteristics
- **Consistent Experience**: Similar visual appearance across different devices
- **Automatic Optimization**: No need to manually adjust for different screen sizes
- **Future-Proof**: Works with new devices and screen sizes automatically
- **Performance**: Lightweight calculations with no external dependencies 