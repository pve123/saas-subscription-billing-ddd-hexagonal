plugins {
    kotlin("jvm")
    kotlin("plugin.jpa")
    kotlin("kapt")
    id("io.spring.dependency-management")
}

dependencies {
    implementation(project(":customer-context"))
    //Flyway
    implementation("org.flywaydb:flyway-core:9.22.3")
    //PostgreSQL
    implementation("org.postgresql:postgresql:42.7.4")
    //JPA
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.5.7")
    //QueryDsl
    annotationProcessor ("com.querydsl:querydsl-apt:5.1.0:jakarta")
    annotationProcessor ("jakarta.annotation:jakarta.annotation-api")
    annotationProcessor ("jakarta.persistence:jakarta.persistence-api")
    //Mapstruct
    implementation ("org.mapstruct:mapstruct:1.5.5.Final")
    annotationProcessor ("org.mapstruct:mapstruct-processor:1.5.5.Final")
    annotationProcessor ("org.projectlombok:lombok-mapstruct-binding:0.2.0")

}

