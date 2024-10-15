plugins {
	java
	id("org.springframework.boot") version "3.3.4"
	id("io.spring.dependency-management") version "1.1.6"
}

group = "dev.tarasov"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.amazonaws.serverless:aws-serverless-java-container-springboot3:2.0.3")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.register<Zip>("buildZip") {
	archiveBaseName.set("springBootServerless")

	from(tasks.compileJava)
	from(tasks.processResources)

	into("lib") {
		from(configurations.runtimeClasspath)
	}
}

tasks.named("build") {
	dependsOn("buildZip")
}

