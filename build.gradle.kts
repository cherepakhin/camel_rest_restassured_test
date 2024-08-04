import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

group = "ru.perm.v"
// change version on publishing
version = "0.24.0302.1"
description = "Tests RestAssured for project Camel Rest(kotlin)"

java.sourceCompatibility = JavaVersion.VERSION_11
var springBootVersion = "2.5.6"
var springDependencyManagement = "1.0.3.RELEASE"
var mockitoKotlinVersion = "4.8.0"
var springFoxVersion = "3.0.0"
var allureVersion = "2.20.1"

buildscript {
	var kotlinVersion: String? by extra; kotlinVersion = "1.1.51"

	repositories {
		mavenCentral()
	}

	dependencies {
		classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
	}

}

repositories {
	mavenCentral()
	mavenLocal()
	maven {

		url = uri("http://v.perm.ru:8082/repository/ru.perm.v") //OK
		isAllowInsecureProtocol = true
		credentials {
			username = "admin"
			password = "pass"

// export NEXUS_CI_USER=admin
// echo $NEXUS_CI_USER
//            username = System.getenv("NEXUS_CRED_USR") ?: extra.properties["nexus-ci-username"] as String?
// export NEXUS_CI_PASS=pass
// echo $NEXUS_CI_PASS
//            password = System.getenv("NEXUS_CRED_PASS") ?: extra.properties["nexus-ci-password"] as String?
		}
	}
}

plugins {
	val kotlinVersion = "1.8.21"
	id("org.springframework.boot") version "2.5.6"
	kotlin("jvm") version kotlinVersion
	kotlin("plugin.spring") version kotlinVersion
	id("maven-publish")
	id("io.qameta.allure") version "2.8.1"
	id("jacoco")
	id("io.spring.dependency-management") version "1.0.9.RELEASE"
	kotlin("kapt") version "1.7.0"
	java
	idea
	application
}

apply(plugin = "org.springframework.boot")
apply(plugin = "kotlin-kapt")


repositories {
	mavenCentral()
	maven {
		url = uri("https://plugins.gradle.org/m2/")
	}
	gradlePluginPortal()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web") {
		exclude("org.springframework.boot:spring-boot-starter-tomcat")
	}
	implementation("org.springframework.boot:spring-boot-starter-jetty") // jetty uses less memory

	implementation("org.jetbrains.kotlin:kotlin-reflect")

	kapt("jakarta.annotation:jakarta.annotation-api")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
	testImplementation("org.mockito.kotlin:mockito-kotlin:5.2.0")
	testImplementation("org.mockito:mockito-inline:5.2.0")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
	testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.0-M1")
	testImplementation("org.junit.jupiter:junit-jupiter-engine:5.8.0-M1")
	testImplementation("org.junit.platform:junit-platform-launcher:1.8.0-M1")
	testImplementation("org.junit.platform:junit-platform-runner:1.8.0-M1")

// https://mvnrepository.com/artifact/io.qameta.allure/allure-junit5
	testImplementation("io.qameta.allure:allure-junit5:$allureVersion")
	testImplementation("io.rest-assured:kotlin-extensions:5.4.0")
	implementation("io.qameta.allure:allure-rest-assured:$allureVersion")

	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
}

tasks.withType<KotlinCompile> {
	kotlinOptions.jvmTarget = "11"
}

tasks.withType<Test> {
	allureConfig
	useJUnitPlatform()
	// Show test log
	testLogging {
//        events("standardOut", "started", "passed", "skipped", "failed")
		events("passed", "skipped", "failed")
	}
}

//// remove suffix 'plain' in sonar repository
tasks.jar {
	enabled = true
	// Remove `plain` postfix from jar file name
	archiveClassifier.set("")
}

// Configure Spring Boot plugin task for running the application.
val bootJar by tasks.getting(BootJar::class) {
	enabled = true
}

publishing {
	repositories {
		maven {
			url = uri("http://v.perm.ru:8082/repository/ru.perm.v/")
			isAllowInsecureProtocol = true
			//  publish в nexus "./gradlew publish" из ноута и Jenkins проходит
			// export NEXUS_CRED_USR=admin
			// echo $NEXUS_CRED_USR
			credentials {
				username = System.getenv("NEXUS_CRED_USR")
				password = System.getenv("NEXUS_CRED_PSW")
			}
		}
	}
}

val allureConfig = allure {
	configuration = "testImplementation"
	version = allureVersion
	autoconfigure = true
	aspectjweaver = true
	clean = true
	useJUnit5 {
		version = allureVersion
	}
}