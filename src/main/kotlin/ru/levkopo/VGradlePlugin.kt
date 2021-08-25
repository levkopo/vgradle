package ru.levkopo

import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File
import kotlin.system.exitProcess

class VGradlePlugin: Plugin<Project> {

    override fun apply(project: Project) {
        val sources = File(project.projectDir, "src")
        if(!sources.isDirectory&&sources.mkdir())
            throw Exception("Source directory not created")

        ProcessBuilder("v", "-v").start().apply {
            if(waitFor()!=0)
                throw Exception("V is not installed")
        }

        project.task("compile") { task ->
            if(project.targets.isEmpty())
                project.targets.add("native")

            for(target in project.targets){
                val builder = ProcessBuilder("v", sources.path,
                    "-o", project.buildDir.path+project.name,
                    "-b", target)

                val process = builder.start()
                if(process.waitFor()!=0){
                    task.logger.error(process.errorStream.bufferedReader().use { it.readText() })
                    exitProcess(1)
                }

                task.logger.info(process.inputStream.bufferedReader().use { it.readText() })
            }
        }

        project.task("run") { task ->
            val builder = ProcessBuilder("v", "run", sources.path)
            val process = builder.start()
            if(process.waitFor()!=0){
                task.logger.error(process.errorStream.bufferedReader().use { it.readText() })
                exitProcess(1)
            }

            task.logger.info(process.inputStream.bufferedReader().use { it.readText() })
        }
    }
}