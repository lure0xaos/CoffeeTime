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
}
