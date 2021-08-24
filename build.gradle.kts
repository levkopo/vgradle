plugins {
    id("org.jetbrains.kotlin.jvm") version "1.5.10"
    id("com.gradle.plugin-publish") version "0.15.0"
    id("java-gradle-plugin")
}

group = "ru.levkopo"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(gradleApi())
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
}

gradlePlugin {
    plugins {
        create("vgradle") {
            id = "ru.levkopo.vgradle"
            implementationClass = "ru.levkopo.VGradlePlugin"
        }
    }
}

pluginBundle {
    website = "https://github.com/levkopo/vgradle"
    vcsUrl = "https://github.com/levkopo/vgradle"
    description = "Plugin for V Language"
    tags = listOf("vlang")

    (plugins) {
        "vgradle" {
            displayName = "VGradle"
        }
    }
}