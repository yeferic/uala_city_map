import javax.inject.Inject
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.process.ExecOperations

abstract class InstallGitHooksTask
    @Inject
    constructor(
        private val execOperations: ExecOperations,
    ) : DefaultTask() {
        init {
            description = "Installs Git hooks from the hooks/ directory into .git/hooks/"
            group = "git"
        }

        @TaskAction
        fun installHooks() {
            val sourceHook = project.file(".github/hooks/commit-msg")
            val targetHook = project.file(".git/hooks/commit-msg")

            if (!targetHook.exists()) {
                println("Installing commit-msg hook...")
                sourceHook.copyTo(targetHook)
            }

            if (!System.getProperty("os.name").lowercase().contains("windows")) {
                execOperations.exec {
                    commandLine("chmod", "+x", targetHook.absolutePath)
                }
            }
        }
    }
