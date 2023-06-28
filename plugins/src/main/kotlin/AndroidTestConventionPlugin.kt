import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidTestConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            dependencies {
                "testImplementation"(libs.findLibrary("junit").get())
                "testImplementation"(libs.findLibrary("kotlinx.coroutines.test").get())
                "testImplementation"(libs.findLibrary("mockk.test").get())
                "testImplementation"(libs.findLibrary("kotest.test").get())
                "testImplementation"(libs.findLibrary("robolectric.test").get())
                "androidTestImplementation"(libs.findLibrary("mockk.android.test").get())
                "androidTestImplementation"(libs.findLibrary("androidx.test.ext.junit").get())
                "androidTestImplementation"(libs.findLibrary("espresso.core").get())
                val bom = libs.findLibrary("compose-bom").get()
                add("androidTestImplementation", platform(bom))
                "androidTestImplementation"(libs.findLibrary("ui.test.junit4").get())
                "androidTestImplementation"(libs.findLibrary("ui.tooling").get())
                "androidTestImplementation"(libs.findLibrary("ui.test.manifest").get())
            }
        }
    }

}