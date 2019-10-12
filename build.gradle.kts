import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.50"
}

group = "com.shionfujie"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    api(kotlin("stdlib-jdk8"))

    api ("io.reactivex.rxjava2:rxjava:2.2.10")

    testImplementation("org.junit.jupiter:junit-jupiter:5.5.2")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}