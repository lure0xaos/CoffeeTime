import de.comahe.i18n4k.gradle.plugin.i18n4k
import java.text.SimpleDateFormat
import java.util.*

val appMainClass: String = "gargoyle.ct.CT"
val appName: String = "CoffeeTime"

val os: org.gradle.internal.os.OperatingSystem = org.gradle.internal.os.OperatingSystem.current()
val appInstallerType: String = "msi"
val appIconIco: String = "app/src/main/resources/gargoyle/ct/bw-icon128.ico"
val appIconPng: String = "app/src/main/resources/gargoyle/ct/bw-icon128.png"
val appCopyright: String = "Lure of Chaos"
val appVendor: String = "Lure of Chaos"

group = "gargoyle.coffee-time"
version = project.extra["project.version"].toString()
description = project.extra["project.description"].toString()

plugins {
    java
    application
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("org.javamodularity.moduleplugin")
    id("org.beryx.jlink")
    id("de.comahe.i18n4k")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("reflect"))
    implementation(project(":util"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-properties:1.5.0")
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
    implementation("de.comahe.i18n4k:i18n4k-core:0.5.0")
    implementation("de.comahe.i18n4k:i18n4k-core-jvm:0.5.0")
}

i18n4k {
    sourceCodeLocales = listOf("en", "ru")
    inputDirectory = "src/main/resources"
}

tasks.compileJava {
    modularity.inferModulePath.set(true)
    options.encoding = Charsets.UTF_8.toString()
}

tasks.compileKotlin {
    destinationDirectory.set(tasks.compileJava.get().destinationDirectory)
    kotlinOptions {
//        languageVersion = "1.9"
    }
}

tasks.compileTestKotlin {
    kotlinOptions {
    }
}

tasks.processResources {
    filesMatching(listOf("**/*.properties", "**/*.html")) {
        filter { line ->
            val transform: (MatchResult) -> CharSequence = { result ->
                result.groups[1]?.value?.let { key ->
                    when {
                        key == "project.name" -> appName
                        key == "project.version" -> project.version.toString()
                        key == "project.description" -> project.description
                        key == "timestamp" -> SimpleDateFormat("yyyyMMdd-HHmm").format(Date())
                        project.extra.has(key) -> project.extra.get(key)?.toString()
                        else -> null
                    }
                } ?: result.groups[0]?.value.toString()
            }
            line.replace(Regex("\\$\\{([^}]+)\\}"), transform).replace(Regex("@([^@]+)@"), transform)
        }
    }
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    dependsOn(tasks.named("generateI18n4kFiles"))
}

tasks.jar {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.build {
    dependsOn += tasks.jpackage
}


application {
    mainClass.set(appMainClass)
}

jlink {
    options.set(listOf("--strip-debug", "--compress", "2", "--no-header-files", "--no-man-pages"))
    launcher {
        name = appName
        mainClass.set(appMainClass)
    }
    jpackage {
        installerType = appInstallerType
        installerName = appName
        appVersion = project.version.toString()
        if (os.isWindows) {
            icon = rootProject.file(appIconIco).path
            installerOptions = listOf(
                "--description", rootProject.description,
                "--copyright", appCopyright,
                "--vendor", appVendor,
                "--win-dir-chooser",
                "--win-menu",
                "--win-per-user-install",
                "--win-shortcut"
            )
        }
        if (os.isLinux) {
            icon = rootProject.file(appIconPng).path
            installerOptions = listOf(
                "--description", rootProject.description,
                "--copyright", appCopyright,
                "--vendor", appVendor,
                "--linux-shortcut"
            )
        }
        if (os.isMacOsX) {
            icon = rootProject.file(appIconPng).path
        }
//        imageOptions = listOf("--win-console")
    }
}
