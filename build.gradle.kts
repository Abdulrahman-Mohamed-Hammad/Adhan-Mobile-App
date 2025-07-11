// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
  id ("org.jetbrains.kotlin.plugin.serialization") version "2.0.20" apply false
    alias(libs.plugins.compose.compiler)   apply false
    id ("com.autonomousapps.dependency-analysis") version "1.20.0"

}