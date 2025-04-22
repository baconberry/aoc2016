plugins {
    kotlin("jvm") version "2.1.20"
    application
}

group = "org.baconberry"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}

application {
    mainClass.set("MainKt")
}

tasks.run.invoke {
    standardInput = System.`in`
}