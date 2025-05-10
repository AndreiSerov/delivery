import com.google.protobuf.gradle.id

plugins {
    val kotlinVersion = "2.1.10"

    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    kotlin("plugin.jpa") version kotlinVersion
    kotlin("plugin.allopen") version kotlinVersion

    id("org.springframework.boot") version "3.4.2"
    id("io.spring.dependency-management") version "1.1.7"

    id("org.openapi.generator") version "7.12.0"

    id("com.google.protobuf") version "0.9.5"

    id("org.jlleitschuh.gradle.ktlint") version "12.2.0"
    jacoco
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.arrow-kt:arrow-core:2.0.1")
    implementation("org.springframework.boot:spring-boot-autoconfigure")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-database-postgresql")
    runtimeOnly("org.postgresql:postgresql")

    implementation("io.grpc:grpc-kotlin-stub:1.4.1")
    implementation("io.grpc:grpc-protobuf:1.71.0")
    implementation("com.google.protobuf:protobuf-kotlin:4.30.2")

    protobuf(files("proto"))

    val kotestVersion = "5.7.2"
    testImplementation("io.kotest:kotest-property:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")

    testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.3")

    testImplementation("io.zonky.test:embedded-database-spring-test:2.6.0")
    testImplementation("io.zonky.test:embedded-postgres:2.1.0")
    testImplementation("io.mockk:mockk:1.13.10")
    testImplementation("com.ninja-squad:springmockk:4.0.2")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
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
                        "**/*Application*.*",
                    )
                }
            },
        ),
    )
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.Embeddable")
    annotation("jakarta.persistence.MappedSuperclass")
}

openApiGenerate {
    inputSpec = "openapi.yml"
    generatorName = "kotlin-spring"
    outputDir = "$projectDir"
    generateApiTests = false
    generateModelTests = false
    generateApiDocumentation = false
    generateModelDocumentation = false
    supportingFilesConstrainedTo = listOf()
    modelPackage = "org.example.api.adapter.http"
    apiPackage = "org.example.api.adapter.http"
    configOptions = mapOf(
        "basePackage" to "org.example.api.config",
        "interfaceOnly" to "true",
        "exceptionHandler" to "false",
        "gradleBuildFile" to "false",
        "skipDefaultInterface" to "true",
        "useSpringBoot3" to "true",
        "useTags" to "true",
    )
}

ktlint {
    version.set("1.4.1")
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:4.30.2"
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.71.0"
        }
    }
    generateProtoTasks {
        ofSourceSet("main").forEach {
            it.plugins {
                id("grpc") { }
            }
        }
    }
}
