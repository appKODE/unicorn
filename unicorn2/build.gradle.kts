plugins {
  id(libs.plugins.kotlinMultiplatform.get().pluginId)
  alias(libs.plugins.vanniktech.maven.publish)
  alias(libs.plugins.kotest)
  alias(libs.plugins.ksp)
  alias(libs.plugins.kover)
}

group = providers.gradleProperty("pomGroupId").get()
version = providers.gradleProperty("versionName").get()

kotlin {
  explicitApi()
  jvmToolchain(11)

  jvm()
  iosArm64()
  iosSimulatorArm64()
  iosX64()
  macosArm64()
  macosX64()

  sourceSets {
    // Kotest's KSP-generated native test runner uses kotlin.time.Clock
    matching { it.name.endsWith("Test") }.configureEach {
      languageSettings.optIn("kotlin.time.ExperimentalTime")
    }
    val commonMain by getting {
      dependencies {
        implementation(libs.bundles.coroutines)
      }
    }
    val commonTest by getting {
      dependencies {
        implementation(libs.bundles.koTestCommon)
        implementation(libs.turbine)
      }
    }
    val jvmTest by getting {
      dependencies {
        implementation(libs.bundles.koTestJvm)
      }
    }
  }
}

mavenPublishing {
  coordinates(artifactId = "unicorn2")

  publishToMavenCentral()
  signAllPublications()

  pom {
    val pomName: String by project
    val pomDescription: String by project
    val pomUrl: String by project
    val pomScmUrl: String by project
    val pomScmConnection: String by project
    val pomScmDevConnection: String by project
    val pomLicenseName: String by project
    val pomLicenseUrl: String by project
    val pomLicenseDist: String by project
    val pomDeveloperId: String by project
    val pomDeveloperName: String by project

    name.set(pomName)
    description.set(pomDescription)
    url.set(pomUrl)

    scm {
      url.set(pomScmUrl)
      connection.set(pomScmConnection)
      developerConnection.set(pomScmDevConnection)
    }

    licenses {
      license {
        name.set(pomLicenseName)
        url.set(pomLicenseUrl)
        distribution.set(pomLicenseDist)
      }
    }

    developers {
      developer {
        id.set(pomDeveloperId)
        name.set(pomDeveloperName)
      }
    }
  }
}

tasks.named<Test>("jvmTest") {
  useJUnitPlatform()
  testLogging {
    showExceptions = true
    showStandardStreams = true
    events = setOf(
      org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED,
      org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED,
      org.gradle.api.tasks.testing.logging.TestLogEvent.STANDARD_OUT,
    )
    exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
  }
}

kover {
  reports {
    filters {
      excludes {
        // Sample.kt is demo code living in main sources
        classes(
          "ru.dimsuz.unicorn2.SampleKt*",
          "ru.dimsuz.unicorn2.CompoundState*",
          "ru.dimsuz.unicorn2.State",
          "ru.dimsuz.unicorn2.ViewState*",
        )
      }
    }
    verify {
      rule {
        minBound(100)
      }
    }
  }
}
