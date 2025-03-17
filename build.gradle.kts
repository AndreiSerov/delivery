plugins {
    val kotlinVersion = "1.9.24"

    kotlin("jvm") version kotlinVersion
    id("io.arrow-kt.analysis.kotlin") version "2.0.2"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    val kotestVersion = "5.7.2"

    implementation("io.arrow-kt:arrow-core:2.0.1")
    testImplementation ("io.kotest:kotest-property:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

application {
    mainClass = "org.example.AppKt"
}
