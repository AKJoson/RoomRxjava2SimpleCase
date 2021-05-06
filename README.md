# RoomRxjava2SimpleCase
### When I import the package in the following way:
```
    compile "android.arch.persistence.room:runtime:1.0.0-alpha5"
    annotationProcessor "android.arch.persistence.room:compiler:1.0.0-alpha5"
    compile "android.arch.persistence.room:rxjava2:1.0.0-alpha5"
```
### An error will occur：
```
Deletion methods must either return void or return int (the number of deleted rows).
```
### I don't kown what happen. But use the way like this，everything will be okay.
```
    def room_version = "2.3.0"
    implementation "androidx.room:room-runtime:$room_version"
    kapt "androidx.room:room-compiler:$room_version"
    implementation "androidx.room:room-rxjava2:2.2.5"
```
