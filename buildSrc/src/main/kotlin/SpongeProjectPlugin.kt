import org.gradle.api.Plugin
import org.gradle.api.Project

class SpongeProjectPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        target.dependencies.apply {
            add("testImplementation", Libs.Test.junit)
            add("testImplementation", Libs.Test.hamcrest)
            add("testImplementation", Libs.Test.mockito)
        }
    }
}