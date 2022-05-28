-printmapping mapping.txt
-verbose
-dontoptimize
-dontpreverify
-dontshrink
-keepattributes Signature
-dontskipnonpubliclibraryclassmembers
-dontusemixedcaseclassnames
-keepparameternames
-renamesourcefileattribute SourceFile
-keepattributes *Annotation*
-keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,*Annotation*,EnclosingMethod

-keep class * extends android.app.Activity
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
}
-keepclassmembers class com.google.firebase** {
  *;
  }
  -keepclassmembers class com.google.android** {
    *;
    }
# Suppress warnings if you are NOT using IAP:

# For using GSON @Expose annotation
-keepattributes *Annotation*
#Keep native
-keepclasseswithmembernames class * {
    native <methods>;
}