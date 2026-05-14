# DeepFake Detector Android App

A comprehensive Android application for detecting deepfake images using TensorFlow Lite machine learning models.

## Project Structure

```
app/src/main/
├── java/com/example/deepfakedetector/
│   ├── ui/
│   │   ├── splash/
│   │   │   └── SplashActivity.kt          # Splash screen activity
│   │   ├── main/
│   │   │   └── MainActivity.kt            # Main image selection and analysis
│   │   ├── result/
│   │   │   └── ResultActivity.kt          # Display detection results
│   │   └── history/
│   │       └── HistoryActivity.kt         # View detection history
│   │
│   ├── data/
│   │   ├── model/
│   │   │   ├── DetectionResult.kt         # Detection result data model
│   │   │   └── HistoryItem.kt             # History item data model
│   │   │
│   │   ├── local/
│   │   │   ├── HistoryDatabaseHelper.kt   # SQLite database management
│   │   │   └── PreferenceManager.kt       # SharedPreferences management
│   │   │
│   │   └── remote/
│   │       ├── ApiService.kt              # Retrofit API endpoints
│   │       ├── RetrofitClient.kt          # Retrofit client configuration
│   │       ├── RequestModels.kt           # API request data models
│   │       └── ResponseModels.kt          # API response data models
│   │
│   ├── ml/
│   │   ├── ImagePreprocessor.kt           # Image preprocessing utilities
│   │   ├── TFLiteClassifier.kt            # TensorFlow Lite model inference
│   │   └── ModelConstants.kt              # ML model constants
│   │
│   ├── repository/
│   │   └── DetectionRepository.kt         # Data repository layer
│   │
│   ├── utils/
│   │   ├── ImageUtils.kt                  # Image manipulation utilities
│   │   ├── FileUtils.kt                   # File management utilities
│   │   ├── PermissionUtils.kt             # Android permission utilities
│   │   └── Constants.kt                   # App-wide constants
│   │
│   └── adapter/
│       └── HistoryAdapter.kt              # RecyclerView adapter for history
│
└── res/
    ├── layout/
    │   ├── activity_splash.xml            # Splash screen layout
    │   ├── activity_main.xml              # Main activity layout
    │   ├── activity_result.xml            # Result activity layout
    │   ├── activity_history.xml           # History activity layout
    │   └── item_history.xml               # History list item layout
    │
    ├── drawable/
    │   ├── ic_launcher_background.xml
    │   └── ic_launcher_foreground.xml
    │
    ├── mipmap/
    │   └── ic_launcher* (various sizes)
    │
    ├── values/
    │   ├── colors.xml                     # Color definitions
    │   ├── strings.xml                    # String resources
    │   └── themes.xml                     # App themes
    │
    └── xml/
        └── (backup and extraction rules)
```

## Key Features

- **Image Selection**: Capture images using camera or select from gallery
- **Deepfake Detection**: Analyze images using TensorFlow Lite model
- **Result Display**: Show detection confidence and classification results
- **History Management**: Store and view past detection results
- **Database**: Local SQLite database for storing detection history
- **Preferences**: Manage app preferences and user settings

## Architecture

The app follows a clean architecture pattern with:
- **UI Layer**: Activities and Adapters for user interface
- **Data Layer**: Repository pattern for data management
- **ML Layer**: TensorFlow Lite classifier for predictions
- **Utility Layer**: Helper functions for common operations

## Dependencies

### Core Android
- androidx.appcompat:appcompat
- androidx.constraintlayout:constraintlayout
- androidx.recyclerview:recyclerview

### Networking
- com.squareup.retrofit2:retrofit
- com.squareup.retrofit2:converter-gson
- com.google.code.gson:gson

### Machine Learning
- org.tensorflow:tensorflow-lite
- org.tensorflow:tensorflow-lite-support
- org.tensorflow:tensorflow-lite-gpu

### Image Processing
- com.github.bumptech.glide:glide

### Coroutines
- org.jetbrains.kotlinx:kotlinx-coroutines-android

## Getting Started

1. Clone the repository
2. Open in Android Studio
3. Add your TensorFlow Lite model file to `assets/model_deepfake.tflite`
4. Build and run the application

## Required Permissions

- `android.permission.CAMERA` - For camera access
- `android.permission.READ_EXTERNAL_STORAGE` - For gallery access
- `android.permission.WRITE_EXTERNAL_STORAGE` - For storing results
- `android.permission.INTERNET` - For API calls

## API Configuration

Update the base URL in `RetrofitClient.kt`:
```kotlin
private const val BASE_URL = "https://api.deepfakedetector.com/"
```

## Database

The app uses SQLite for storing detection history with the following schema:
- id (TEXT PRIMARY KEY)
- image_uri (TEXT)
- label (TEXT)
- confidence_score (REAL)
- timestamp (LONG)

## Model Configuration

Update model constants in `ModelConstants.kt`:
- Input size: 224x224 pixels
- Confidence threshold: 0.5
- Model version: 1.0.0

## Build Configuration

The app is built with:
- Minimum SDK: 24
- Target SDK: 36
- Kotlin language level: 11
- View binding enabled for Activities

"# DeepfakeDetector" 
