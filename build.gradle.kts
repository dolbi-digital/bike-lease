plugins {
    kotlin("jvm") version "1.9.24"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))

    testImplementation("net.serenity-bdd:serenity-core:4.2.2")
    testImplementation("net.serenity-bdd:serenity-junit5:4.2.2")
    testImplementation("org.seleniumhq.selenium:selenium-java:4.11.0")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.13.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.13.2")

    implementation("io.rest-assured:rest-assured:5.3.0")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.2")

    testImplementation("io.github.bonigarcia:webdrivermanager:5.4.1")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}

application {
    mainClass.set("MainKt")
}