rootProject.name = "otus_java"
include("hw01-gradle")
include("hw02-collections")
include("hw03-annotations")

pluginManagement {
    val jgitverPluginVersion: String by settings
    val springPluginVersion: String by settings
    val springBootPluginVersion: String by settings
    val shadowJarPluginVersion: String by settings
    val sonarPluginVersion: String by settings
    val spotlessPluginVersion: String by settings

    plugins {
        id("fr.brouillard.oss.gradle.jgitver") version jgitverPluginVersion
        id("io.spring.dependency-management") version springPluginVersion
        id("org.springframework.boot") version springBootPluginVersion
        id("com.github.johnrengelman.shadow") version shadowJarPluginVersion
        id("name.remal.sonarlint") version sonarPluginVersion
        id("com.diffplug.spotless") version spotlessPluginVersion
    }
}