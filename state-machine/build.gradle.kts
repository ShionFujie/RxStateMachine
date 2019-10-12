import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
}

dependencies {
    implementation(Deps.kotlinStdlib)

    api(Deps.rxJava)

    testImplementation(project(":testing-utils"))
    testImplementation(Deps.junitJupiter)
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.withType<Test> {
    useJUnitPlatform()
}