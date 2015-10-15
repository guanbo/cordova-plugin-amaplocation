Location Cordova Plugin 
===


## Configuration

`build-extras.gradle`

```
android {
    defaultConfig {
        manifestPlaceholders = [AMAP_API_KEY_VALUE : "debug_key"]
    }
    buildTypes {
        release {
            manifestPlaceholders = [AMAP_API_KEY_VALUE : "release_key"]
        }
    }
}
```