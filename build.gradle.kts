plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.14.4")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.14.4")
    testImplementation("org.junit.platform:junit-platform-commons:6.0.0")
    testRuntimeOnly("org.junit.platform:junit-platform-engine:6.1.1")

}

tasks.test {
    useJUnitPlatform()
}