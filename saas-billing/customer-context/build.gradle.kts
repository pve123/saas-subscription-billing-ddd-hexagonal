plugins {
	kotlin("jvm")
}

dependencies {
    implementation(project(":common"))
    implementation("org.springframework:spring-context")
    implementation ("org.mapstruct:mapstruct:1.5.5.Final")
    annotationProcessor ("org.mapstruct:mapstruct-processor:1.5.5.Final")
    annotationProcessor ("org.projectlombok:lombok-mapstruct-binding:0.2.0")
}

