plugins {
  alias(libs.plugins.spotless)
  alias(libs.plugins.kotlinMultiplatform) apply false
  alias(libs.plugins.vanniktech.maven.publish) apply false
}

spotless {
  kotlin {
    target("**/*.kt")
    targetExclude("**/build/**/*.*")
    ktlint(libs.versions.ktlint.get()).editorConfigOverride(mapOf("indent_size" to "2", "max_line_length" to "120"))
    trimTrailingWhitespace()
    endWithNewline()
  }

  kotlinGradle {
    target("**/*.gradle.kts")
    ktlint(libs.versions.ktlint.get()).editorConfigOverride(mapOf("indent_size" to "2", "max_line_length" to "120"))
    trimTrailingWhitespace()
    endWithNewline()
  }
}
