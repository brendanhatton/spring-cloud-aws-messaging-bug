import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.gradle.internal.logging.text.StyledTextOutputFactory;
import org.gradle.internal.logging.text.StyledTextOutput.Style;
import org.gradle.kotlin.dsl.support.serviceOf

plugins {
    id("org.springframework.boot") version "2.4.4"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.4.10"
    kotlin("plugin.spring") version "1.4.10"
}

extra["springCloudAWSVersion"] = "2.3.0"

dependencyManagement {
    imports {
        mavenBom("io.awspring.cloud:spring-cloud-aws-dependencies:${property("springCloudAWSVersion")}")
    }
}

java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("io.awspring.cloud:spring-cloud-aws-messaging")
    implementation("io.awspring.cloud:spring-cloud-aws-autoconfigure")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.register(name = "up", type = Exec::class) {
    commandLine = listOf("docker-compose", "-f", "./local/docker-compose.local.infra.yml", "up", "-d", "local-sqs")
}

tasks.register(name = "down", type = Exec::class) {
    commandLine = listOf("docker-compose", "-f", "./local/docker-compose.local.infra.yml", "rm", "-sf", "local-sqs")
}

