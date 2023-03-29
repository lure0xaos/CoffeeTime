import de.comahe.i18n4k.gradle.plugin.i18n4k
import java.text.SimpleDateFormat
import java.util.*

group = "gargoyle.coffee-time"
version = project.extra["project.version"].toString()

plugins {
    `java-library`
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("org.javamodularity.moduleplugin")
    id("de.comahe.i18n4k")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("reflect"))
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
//    targetCompatibility = javaVersion
    kotlinOptions {
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
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    dependsOn(tasks.named("generateI18n4kFiles"))
}
