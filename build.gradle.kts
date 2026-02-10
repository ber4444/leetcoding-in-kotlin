import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

val kotlinVersion = "2.1"
val ktorVersion = "2.1.0"

plugins {
    kotlin("jvm").version("2.1.0")
    application
}

group = "me.gberenyi"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test-junit"))
    implementation(kotlin("script-runtime"))

    // only needed for the Coroutines folder:
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
    implementation("io.ktor:ktor-client-core:${ktorVersion}")
    implementation("io.ktor:ktor-client-cio:${ktorVersion}")
    implementation("io.ktor:ktor-client-content-negotiation:${ktorVersion}")
    implementation("io.ktor:ktor-serialization-gson:${ktorVersion}")
    implementation("com.google.code.gson:gson:2.7")
}

tasks.test {
    useJUnit()
}

kotlin {
    // Extension level
    compilerOptions {
        jvmTarget = JvmTarget.fromTarget("21")
        languageVersion = KotlinVersion.fromVersion(kotlinVersion)
        apiVersion = KotlinVersion.fromVersion(kotlinVersion)
    }
}

// Example of overriding at compilation unit level
tasks.named<KotlinJvmCompile>("compileKotlin"){
    compilerOptions {
        apiVersion = KotlinVersion.fromVersion(kotlinVersion)
    }
}

application {
    mainClass = "MainKt"
}
