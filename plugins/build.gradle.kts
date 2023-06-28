plugins {
    `kotlin-dsl`
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.hilt.gradlePlugin)
    kotlin("plugin.serialization")
}


gradlePlugin {
    plugins {
        register("androidLibraryCompose") {
            id = "scotia.android.library.compose"
            implementationClass = "ComposeLibraryConventionPlugin"
        }

        register("androidLibrary") {
            id = "scotia.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }

        register("androidHilt") {
            id = "scotia.android.hilt"
            implementationClass = "HiltConventionPlugin"
        }

        register("androidRetrofit") {
            id = "scotia.android.retrofit"
            implementationClass = "RetrofitConventionPlugin"
        }

        register("androidTest") {
            id = "scotia.android.test"
            implementationClass = "AndroidTestConventionPlugin"
        }
    }
}

