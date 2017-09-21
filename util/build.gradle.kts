import java.text.SimpleDateFormat
import java.util.*

val javaVersion: String = JavaVersion.VERSION_11.toString()

group = "gargoyle.coffee-time"
version = project.extra["project.version"].toString()

plugins {
    kotlin("jvm") version "1.6.21"

    id("org.javamodularity.moduleplugin") version ("1.8.11")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("reflect"))
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

tasks.processResources {
    filesMatching(listOf("**/*.properties", "**/*.html")) {
        filter { line ->
            val transform: (MatchResult) -> CharSequence = { result ->
                result.groups[1]?.value?.let { key ->
                    when {
                        key == "project.name" -> project.name
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
