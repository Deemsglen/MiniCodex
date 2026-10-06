package com.minicodex.feature.editor

import android.content.Context
import java.io.File

/**
 * Manages files and directories within a project workspace.
 */
class ProjectManager(private val context: Context) {
    private var currentProjectDir: File? = null

    fun createProject(name: String): File {
        val projectDir = File(context.filesDir, "projects/$name")
        if (!projectDir.exists()) {
            projectDir.mkdirs()
        }
        currentProjectDir = projectDir
        return projectDir
    }

    fun writeFile(path: String, content: String): String {
        val file = File(currentProjectDir, path)
        file.parentFile?.mkdirs()
        file.writeText(content)
        return "Successfully wrote to $path"
    }

    fun readFile(path: String): String {
        val file = File(currentProjectDir, path)
        if (!file.exists()) return "Error: File $path not found"
        return file.readText()
    }

    fun listFiles(path: String = ""): List<File> {
        val dir = File(currentProjectDir, path)
        return dir.listFiles()?.toList() ?: emptyList()
    }

    fun deleteFile(path: String): String {
        val file = File(currentProjectDir, path)
        if (file.delete()) return "Deleted $path"
        return "Error deleting $path"
    }
}
