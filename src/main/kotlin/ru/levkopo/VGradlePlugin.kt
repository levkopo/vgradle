package ru.levkopo

import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File
import kotlin.system.exitProcess

class VGradlePlugin: Plugin<Project> {

    override fun apply(project: Project) {
        val sources = File(project.rootDir, "src")
        if(!sources.isDirectory&&sources.mkdir())
            throw Exception("Source directory not created")

        project.task("compile") { task ->
            val builder = ProcessBuilder("v", sources.path, "-o", project.buildDir.path+project.name)
            val process = builder.start()
            if(process.waitFor()!=0){
                task.logger.error(process.errorStream.bufferedReader().use { it.readText() })
                exitProcess(1)
            }
        }
    }
}