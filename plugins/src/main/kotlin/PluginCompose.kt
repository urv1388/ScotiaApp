
import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

/**
 * Configure Compose-specific options
 */
internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *, *>,
) {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

    commonExtension.apply {
        buildFeatures {
            compose = true
        }

        composeOptions {
            kotlinCompilerExtensionVersion =
                libs.findVersion("compose.compiler").get().toString()
        }

        packaging {
            resources {
                excludes += "/META-INF/{AL2.0,LGPL2.1}"
            }
        }

        dependencies {
            val bom = libs.findLibrary("compose-bom").get()
            add("implementation", platform(bom))
            "implementation"(libs.findLibrary("ui").get())
            "implementation"(libs.findLibrary("ui.graphics").get())
            "implementation"(libs.findLibrary("ui.tooling.preview").get())
            "implementation"(libs.findLibrary("material3").get())
            add("androidTestImplementation", platform(bom))
            "androidTestImplementation"(libs.findLibrary("ui.test.junit4").get())
        }
    }
}
