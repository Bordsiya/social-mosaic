import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.2.5"
	id("io.spring.dependency-management") version "1.1.4"
	kotlin("jvm") version "1.9.23"
	kotlin("plugin.spring") version "1.9.23"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.postgresql:postgresql")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	testImplementation("org.springframework.boot:spring-boot-starter-test")

	// logging
	implementation("org.springframework.boot:spring-boot-starter-logging")
	implementation("io.github.microutils:kotlin-logging:2.0.10")

	implementation("org.springframework.boot:spring-boot-starter-data-neo4j")

	// vk
	implementation("com.vk.api:sdk:+")

	// coroutines
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:+")

	// img analyser
	implementation("org.deeplearning4j:deeplearning4j-core:1.0.0-beta7")
	implementation("org.nd4j:nd4j-native-platform:1.0.0-beta7")
	implementation("org.deeplearning4j:deeplearning4j-zoo:1.0.0-beta7")

}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
