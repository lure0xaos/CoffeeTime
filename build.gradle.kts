group = "gargoyle.coffee-time"
version = project.extra["project.version"].toString()
description = project.extra["project.description"].toString()

plugins {
    java
    kotlin("jvm") version "1.8.10" apply false
    kotlin("plugin.serialization") version "1.8.10" apply false
    id("org.javamodularity.moduleplugin") version "1.8.12" apply false
    id("org.beryx.jlink") version "2.26.0" apply false
    id("de.comahe.i18n4k") version "0.5.0" apply false
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-properties:1.5.0")
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
    implementation("de.comahe.i18n4k:i18n4k-core:0.5.0")
    implementation("de.comahe.i18n4k:i18n4k-core-jvm:0.5.0")
}
