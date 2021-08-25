package ru.levkopo

import org.gradle.api.Project

val Project.targets
    get() = mutableListOf<String>()

class TargetsHandlerScope: ArrayList<String>() {
    fun js() = add("js")
    fun native() = add("native")
    fun custom(target: String) = add(target)
}

fun Project.targets(configuration: TargetsHandlerScope.() -> Unit) =
    targets.addAll(TargetsHandlerScope().apply {
        this.configuration()
    })