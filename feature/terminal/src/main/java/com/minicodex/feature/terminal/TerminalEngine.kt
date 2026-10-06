package com.minicodex.feature.terminal

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * A simplified terminal execution engine for Android.
 * In a real app, this would interface with a PTY/bundled shell.
 */
class TerminalEngine {
    suspend fun executeCommand(command: String): String = withContext(Dispatchers.IO) {
        try {
            // Simplified: uses ProcessBuilder for basic shell commands.
            // For full shell support, a bundled binary environment (like Termux) is used.
            val process = ProcessBuilder("sh", "-c", command).start()
            val reader = BufferedReader(InputStreamReader(process.inputStream))
            val errorReader = BufferedReader(InputStreamReader(process.errorStream))

            val output = StringBuilder()
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                output.append(line).append("\n")
            }

            var errorLine: String?
            while (errorReader.readLine().also { errorLine = it } != null) {
                output.append("ERROR: ").append(errorLine).append("\n")
            }

            val exitCode = process.waitFor()
            output.append("\nExit code: $exitCode")
            output.toString()
        } catch (e: Exception) {
            "Execution Error: ${e.message}"
        }
    }
}
