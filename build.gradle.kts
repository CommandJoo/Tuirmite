plugins {
    id("java")
    id("com.gradleup.shadow") version("8.3.3")
}

group = "de.johannes"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {

    //Testing
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation("net.sf.jopt-simple:jopt-simple:4.7")
    implementation("com.google.code.gson:gson:2.11.0")
    implementation("commons-io:commons-io:2.18.0")

    implementation(fileTree(mapOf("dir" to "src/native", "include" to listOf("*.so"))))
    implementation(fileTree(mapOf("dir" to "src/native", "include" to listOf("*.dll"))))
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "de.johannes.Main"
    }
}

tasks.test {
    useJUnitPlatform()
}