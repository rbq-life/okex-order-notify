plugins {
    val kotlinVersion = "1.6.10"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion

    id("net.mamoe.mirai-console") version "2.11.1"
}
mirai {
    coreVersion = "2.11.1" // mirai-core version
}

group = "okex.order.notify"
version = "0.1.0"

repositories {
    mavenLocal()
    mavenCentral()
}
dependencies {
    implementation("ch.qos.logback:logback-classic:1.2.3")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json-jvm:1.3.0")
    implementation("com.squareup.okhttp3:okhttp:4.10.0")
}
