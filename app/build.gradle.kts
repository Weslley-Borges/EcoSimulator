plugins {
    application
}

repositories {
    mavenCentral()
}

configure<SourceSetContainer> {
    named("main") {
        java.srcDir("app/src/main/java")
    }
}

dependencies {
    // Use JUnit test framework.
    testImplementation("junit:junit:4.13.2")

    // This dependency is used by the application.
    implementation("com.google.guava:guava:31.1-jre")
    implementation("com.googlecode.json-simple:json-simple:1.1.1")
    implementation("com.jakewharton.fliptables:fliptables:1.1.0")
}

application {
    // Define the main class for the application.
    mainClass.set("ecosimulator.App")
}
