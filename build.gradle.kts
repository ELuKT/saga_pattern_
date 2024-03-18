plugins {
    id("java")
    id("io.spring.dependency-management") version "1.1.3"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

subprojects {
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "java")
    dependencyManagement {
        imports {
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:2022.0.4")
        }
        imports {
            mavenBom("org.springframework.cloud:spring-cloud-commons:4.0.4")
        }
    }
    dependencies {
        compileOnly("org.projectlombok:lombok:1.18.28")
        annotationProcessor("org.projectlombok:lombok:1.18.28")
        testImplementation(platform("org.junit:junit-bom:5.9.1"))
        testImplementation("org.junit.jupiter:junit-jupiter")

    }
}

tasks.test {
    useJUnitPlatform()
}