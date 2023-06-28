
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class RetrofitConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.plugin.serialization")
                apply("kotlin-parcelize")
            }

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            dependencies {
                "implementation"(libs.findLibrary("okhttp.logging").get())
                "implementation"(libs.findLibrary("retrofit.core").get())
                "implementation"(libs.findLibrary("retrofit.kotlin.serialization").get())
                "implementation"(libs.findLibrary("kotlinx.serialization.json").get())
            }
        }
    }

}