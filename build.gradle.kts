plugins {
    val kotlinVersion = "2.1.10"

    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion

    application

    id("org.springframework.boot") version "3.4.2"
    id("io.spring.dependency-management") version "1.1.7"
    jacoco
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.arrow-kt:arrow-core:2.0.1")
    implementation("org.springframework.boot:spring-boot-autoconfigure")


    val kotestVersion = "5.7.2"
    testImplementation ("io.kotest:kotest-property:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")

    testImplementation("io.mockk:mockk:1.13.10")
    testImplementation("com.ninja-squad:springmockk:4.0.2")
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(true)
    }
    classDirectories.setFrom(
        files(
            classDirectories.files.map {
                fileTree(it) {
                    exclude(
                        "**/config",
                        "**/enum",
                        "**/error",
                        "**/entity",
                        "**/exception",
                        "**/dto",
                        "**/logging",
                        "**/repository",
                        "**/*Application*.*"
                    )
                }
            }
        )
    )
}


java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

application {
    mainClass = "org.example.AppKt"
}
