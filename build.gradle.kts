import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version Versions.kotlin
}

repositories {
    mavenCentral()
}

allprojects {
    group = "com.shionfujie"
    version = "1.0-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}