plugins {
    kotlin("jvm") version "2.1.10"
    kotlin("plugin.serialization") version "1.8.22"
    jacoco
}
group = "org.baghdad"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    testImplementation(kotlin("test"))

    // koin
    implementation("io.insert-koin:koin-core:4.0.3")

    // kotlin date time
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.2")

    testImplementation("org.jetbrains.kotlin:kotlin-test")

    // kotest, assertion
    testImplementation("io.kotest:kotest-runner-junit5:5.7.2")
    testImplementation("io.kotest:kotest-assertions-core:5.7.2")

    // google truth
    testImplementation("com.google.truth:truth:1.4.2")

    // mockk
    testImplementation("io.mockk:mockk:1.14.0")

    // junit params
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.10.2")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    // Kotlinx Coroutine
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    // Ktor client
    implementation("io.ktor:ktor-client-core:3.1.2")
    implementation("io.ktor:ktor-client-cio:3.1.2")
    implementation("io.ktor:ktor-client-content-negotiation:3.1.2")
    implementation("io.ktor:ktor-serialization-kotlinx-json:3.1.2")

    // Kotlinx Serialization (needed for JSON serialization)
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")

    // Logging
    implementation("ch.qos.logback:logback-classic:1.5.13")

    testImplementation(kotlin("test"))

    // dotenv
    implementation("com.github.dotenv-org:dotenv-vault-kotlin:0.0.2")

    // Mongo DB
    implementation("org.mongodb:mongodb-driver-kotlin-coroutine:4.10.1")

    // Mongo Test library
    testImplementation("org.mongodb:mongodb-driver-sync:4.10.1")


}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(20)
}

tasks.jacocoTestReport {
    reports {
        csv.required.set(true)  // Enable CSV reports for additional processing if needed
        xml.required.set(true)  // Required for coverage-diff to work
        html.required.set(true) // Human-readable reports
    }
    // Customize the JaCoCo report generation task
    dependsOn(tasks.test)

    // Set up exclusions for certain packages
    classDirectories.setFrom(
        fileTree("build/classes/kotlin/main") {
            exclude("**/di/**") // Exclude DI package
            exclude("**/model/**") // Exclude entities package
            exclude("**/generated/**") // Exclude generated code if any
            exclude("**/main.kt") // Exclude main.kt file
        }
    )

    // Define the source directories for Jacoco
    sourceDirectories.setFrom(files("src/main/kotlin"))
    executionData.setFrom(fileTree(layout.buildDirectory).include("jacoco/test.exec"))
}

tasks.jacocoTestCoverageVerification {
    dependsOn(tasks.test)

    classDirectories.setFrom(
        fileTree("build/classes/kotlin/main") {
            exclude("**/generated/**")
            exclude("**/di/**")
            exclude("**/model/**")
            exclude("**/main.kt") // Exclude main.kt file
            exclude("**/mongoclothesdatasource.kt/**")

        }
    )
    sourceDirectories.setFrom(files("src/main/kotlin"))
    executionData.setFrom(fileTree(layout.buildDirectory).include("jacoco/test.exec"))

    violationRules {
        rule {
            limit {
                minimum = "0.90".toBigDecimal() // 100% coverage requirement
            }
        }
        rule {
            element = "CLASS"
            includes = listOf("org.baghdad.*") // Adjust package name as needed

            limit {
                counter = "LINE"
                value = "COVEREDRATIO"
                minimum = "0.90".toBigDecimal()
            }
            limit {
                counter = "BRANCH"
                value = "COVEREDRATIO"
                minimum = "0.90".toBigDecimal()
            }
            limit {
                counter = "METHOD"
                value = "COVEREDRATIO"
                minimum = "0.90".toBigDecimal()
            }
        }
    }
}


tasks.named<JacocoCoverageVerification>("jacocoTestCoverageVerification") {
    dependsOn(tasks.test)

    classDirectories.setFrom(
        fileTree("build/classes/kotlin/main") {
            exclude("**/generated/**")
            exclude("**/di/**")
            exclude("**/model/**")
            exclude("**/main.kt") // Exclude main.kt file
            exclude("**/mongoclothesdatasource.kt/**")
        }
    )
    sourceDirectories.setFrom(files("src/main/kotlin"))
    executionData.setFrom(fileTree(layout.buildDirectory).include("jacoco/test.exec"))
}
