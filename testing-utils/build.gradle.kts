import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
}

dependencies {
    implementation(Deps.kotlinStdlib)

    api(Deps.junitJupiter)
    api(Deps.rxJava)
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}