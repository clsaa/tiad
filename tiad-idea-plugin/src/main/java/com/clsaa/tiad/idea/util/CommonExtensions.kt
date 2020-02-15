package com.clsaa.tiad.idea.util

import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.project.Project


fun <T> Class<T>.getService(): T {
    return ServiceManager.getService(this)
}

fun <T> Class<T>.getService(project: Project): T {
    return ServiceManager.getService(project, this)
}