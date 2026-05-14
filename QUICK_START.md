# DeepFake Detector - Quick Start Guide

## 🚀 Getting Started

Your DeepFake Detector Android application has been fully scaffolded with all necessary files and structure. Follow these steps to get started:

## 1. Prerequisites

- Android Studio 2023.1 or newer
- Android SDK API 24 or higher
- Kotlin 1.9+
- TensorFlow Lite model file (`.tflite`)

## 2. Project Setup

### 2.1 Open the Project
```bash
# In Android Studio:
File → Open → D:\Android\SDK\DeepFakeDetector
```

### 2.2 Sync Gradle
```bash
File → Sync Now (or press Ctrl+Shift+O)
```

### 2.3 Add Model File
```
1. Download your trained deepfake detection TFLite model
2. Paste into: app/src/main/assets/model_deepfake.tflite
3. If model doesn't exist, create the assets folder first
```

## 3. Configuration

### 3.1 Update API Endpoint (Optional)
Edit: `app/src/main/java/com/example/deepfakedetector/data/remote/RetrofitClient.kt`
```kotlin
private const val BASE_URL = "https://your-api-endpoint.com/"
```

### 3.2 Update Model Input Size (if needed)
Edit: `app/src/main/java/com/example/deepfakedetector/ml/ModelConstants.kt`
```kotlin
const val INPUT_SIZE = 224  // Change if your model uses different size
const val CONFIDENCE_THRESHOLD = 0.5f
```

### 3.3 Configure Database
Database is automatically created on first run in:
```
/data/data/com.example.deepfakedetector/databases/deepfake_history.db
```

## 4. Build the Project

### 4.1 Build APK
```bash
# Debug APK
./gradlew assembleDebug

# Release APK
./gradlew assembleRelease
```

### 4.2 Install on Device
```bash
# Via Android Studio: Run → Run 'app'
# Or via command line:
./gradlew installDebug
```

## 5. Project Structure Overview

```
Kotlin Files (19 total):
├── UI Layer (4 Activities)
│   └── SplashActivity, MainActivity, ResultActivity, HistoryActivity
├── Data Models (2 models)
│   └── DetectionResult, HistoryItem
├── Data Layer (6 files)
│   ├── Local: HistoryDatabaseHelper, PreferenceManager
│   └── Remote: ApiService, RetrofitClient, RequestModels, ResponseModels
├── ML Layer (3 files)
│   └── ImagePreprocessor, TFLiteClassifier, ModelConstants
├── Repository (1 file)
│   └── DetectionRepository
├── Utils (4 files)
│   └── ImageUtils, FileUtils, PermissionUtils, Constants
└── Adapter (1 file)
    └── HistoryAdapter

XML Files (8 total):
├── Layouts (5): activity_main, activity_splash, activity_result, activity_history, item_history
└── Resources (3): colors, strings, themes
```

## 6. Key Files Modification Guide

### Modify Image Input Size
**File**: `ml/ImagePreprocessor.kt`
```kotlin
companion object {
    private const val INPUT_WIDTH = 224   // Change here
    private const val INPUT_HEIGHT = 224  // Change here
}
```

### Modify Detection Threshold
**File**: `ml/ModelConstants.kt`
```kotlin
const val CONFIDENCE_THRESHOLD = 0.5f  // Adjust sensitivity (0.0-1.0)
```

### Modify Database Schema
**File**: `data/local/HistoryDatabaseHelper.kt`
```kotlin
override fun onCreate(db: SQLiteDatabase?) {
    // Modify SQL schema here if needed
}
```

### Modify API Requests
**Files**: 
- `data/remote/RequestModels.kt` - Request structure
- `data/remote/ResponseModels.kt` - Response structure
- `data/remote/ApiService.kt` - Endpoints

## 7. Common Tasks

### Task: Add a New Permission
1. Edit: `app/src/main/AndroidManifest.xml`
2. Add `<uses-permission>` tag in manifest
3. Update: `utils/PermissionUtils.kt` - Add to REQUIRED_PERMISSIONS
4. Check permission at runtime in Activity

### Task: Add a New String Resource
1. Edit: `app/src/main/res/values/strings.xml`
2. Add new `<string>` element
3. Reference in code: `getString(R.string.your_string)`

### Task: Add a New Color
1. Edit: `app/src/main/res/values/colors.xml`
2. Add new `<color>` element
3. Reference: `getColor(R.color.your_color)` or in XML: `@color/your_color`

### Task: Add a New Layout
1. Create: `app/src/main/res/layout/activity_new.xml`
2. Create Activity class: `com.example.deepfakedetector.ui.new.NewActivity.kt`
3. Register in: `AndroidManifest.xml`

## 8. Testing

### Run Unit Tests
```bash
./gradlew test
```

### Run Instrumented Tests
```bash
./gradlew connectedAndroidTest
```

### Test on Emulator
1. Open Android Studio AVD Manager
2. Create or select an emulator (API 24+)
3. Run → Run 'app'

## 9. Debugging

### Enable Logcat
```bash
adb logcat | grep "DeepFakeDetector"
```

### Access Database
```bash
adb shell
cd /data/data/com.example.deepfakedetector/databases/
sqlite3 deepfake_history.db
.tables
SELECT * FROM history;
```

### View SharedPreferences
```bash
adb shell
cd /data/data/com.example.deepfakedetector/shared_prefs/
cat deepfake_detector_prefs.xml
```

## 10. Performance Optimization

### Model Size
- Use quantized TFLite models for faster inference
- Aim for model size < 50MB for mobile

### Image Optimization
- Images are auto-resized to 224x224
- Reduce resolution before sending to API
- Cache processed images

### Database
- Consider pagination for large history
- Archive old records periodically

## 11. Troubleshooting

### Build Fails
```bash
# Clean and rebuild
./gradlew clean
./gradlew build
```

### Model Not Found
- Ensure `model_deepfake.tflite` is in `assets/` folder
- Check filename matches `ModelConstants.MODEL_FILE_NAME`

### Permissions Denied
- Run on physical device or emulator with proper permissions
- Grant permissions when prompted
- Check manifest has required `<uses-permission>` tags

### API Connection Error
- Verify `BASE_URL` in RetrofitClient.kt
- Check internet permission in manifest
- Test API endpoint separately

## 12. Documentation Files

- **README.md** - Full project documentation
- **PROJECT_STRUCTURE.md** - Detailed architecture
- **FILE_MANIFEST.md** - Complete file listing
- **QUICK_START.md** - This file

## 13. Next Steps

1. ✅ Copy TFLite model to assets/
2. Add additional features as needed
3. Implement unit tests
4. Test on various devices
5. Optimize performance
6. Release to Play Store

## 14. Support Resources

- [Android Documentation](https://developer.android.com/)
- [TensorFlow Lite Guide](https://www.tensorflow.org/lite/android)
- [Retrofit Documentation](https://square.github.io/retrofit/)
- [Material Design](https://material.io/design)

## 15. Final Checklist

Before deploying to production:

- [ ] Add TFLite model file
- [ ] Update API endpoint
- [ ] Test on multiple devices
- [ ] Test all permissions
- [ ] Verify camera functionality
- [ ] Test gallery access
- [ ] Check database operations
- [ ] Test history display
- [ ] Verify result calculations
- [ ] Test error handling
- [ ] Update app version
- [ ] Sign APK for release
- [ ] Test on Play Store beta

---

**Status**: 🟢 Ready for Development

Your project is fully set up and ready to be developed further!

