import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10"
    application
}

group = "me.valentinegb"
version = "1.0.0"

repositories {
    mavenCentral()
}

tasks {
    val fatJar = register<Jar>("fatJar") {
        dependsOn.addAll(
            listOf("compileJava", "compileKotlin", "processResources")
        )
        archiveClassifier.set("standalone")

        duplicatesStrategy = DuplicatesStrategy.EXCLUDE

        manifest {
            attributes(
                mapOf("Main-Class" to application.mainClass)
            )
        }

        from(
            configurations.runtimeClasspath.get()
                .map {
                    if (it.isDirectory) it else zipTree(it)
                } + sourceSets.main.get().output
        )
    }

    build {
        dependsOn(fatJar)
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}