import java.text.SimpleDateFormat
import java.util.*

val javaVersion: String = JavaVersion.VERSION_11.toString()

val appMainClass: String = "gargoyle.ct.CT"
val appModule: String = "CoffeeTime"
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
    application
    kotlin("jvm") version "1.6.21"

    id("org.javamodularity.moduleplugin") version ("1.8.11")
    id("org.beryx.jlink") version ("2.25.0")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("reflect"))
    implementation(project(":util"))
}

tasks.compileJava {
    modularity.inferModulePath.set(true)
    sourceCompatibility = javaVersion
    targetCompatibility = javaVersion
    options.encoding = Charsets.UTF_8.toString()
}

tasks.compileKotlin {
    destinationDirectory.set(tasks.compileJava.get().destinationDirectory)
    targetCompatibility = javaVersion
    kotlinOptions {
        jvmTarget = javaVersion
    }
}

tasks.compileTestKotlin {
    kotlinOptions {
        jvmTarget = javaVersion
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
}

tasks.jar {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.build {
    dependsOn += tasks.jpackage
}


application {
    mainClass.set(appMainClass)
    mainModule.set(appModule)
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
