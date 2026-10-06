package com.minicodex.feature.agent

import com.minicodex.core.ai.ToolDefinition
import com.minicodex.core.ai.ToolParameters
import com.minicodex.core.ai.ParameterProperty
import com.minicodex.core.ai.ToolCall

/**
 * Dispatches AI tool calls to actual system implementations.
 */
interface ToolDispatcher {
    fun getDefinitions(): List<ToolDefinition>
    suspend fun dispatch(toolCall: ToolCall): String
}

class MiniCodexToolDispatcher(
    private val fileTool: FileTool,
    private val terminalTool: TerminalTool,
    private val browserTool: BrowserTool
) : ToolDispatcher {

    override fun getDefinitions(): List<ToolDefinition> = listOf(
        ToolDefinition("file_read", "Read contents of a file",
            ToolParameters("object", mapOf("path" to ParameterProperty("string", "Path to file")))),
        ToolDefinition("file_write", "Write content to a file",
            ToolParameters("object", mapOf("path" to ParameterProperty("string", "Path to file"), "content" to ParameterProperty("string", "Content to write")))),
        ToolDefinition("terminal_execute", "Execute a shell command",
            ToolParameters("object", mapOf("command" to ParameterProperty("string", "Command to run")))),
        ToolDefinition("browser_open", "Open a URL in browser",
            ToolParameters("object", mapOf("url" to ParameterProperty("string", "URL to open")))),
        ToolDefinition("browser_click", "Click an element on page",
            ToolParameters("object", mapOf("selector" to ParameterProperty("string", "CSS selector"))))
    )

    override suspend fun dispatch(toolCall: ToolCall): String {
        return when (toolCall.toolName) {
            "file_read" -> fileTool.read(toolCall.arguments["path"] as String)
            "file_write" -> fileTool.write(toolCall.arguments["path"] as String, toolCall.arguments["content"] as String)
            "terminal_execute" -> terminalTool.execute(toolCall.arguments["command"] as String)
            "browser_open" -> browserTool.open(toolCall.arguments["url"] as String)
            "browser_click" -> browserTool.click(toolCall.arguments["selector"] as String)
            else -> "Error: Unknown tool ${toolCall.toolName}"
        }
    }
}

// Tool interfaces
interface FileTool {
    suspend fun read(path: String): String
    suspend fun write(path: String, content: String): String
}

interface TerminalTool {
    suspend fun execute(command: String): String
}

interface BrowserTool {
    suspend fun open(url: String): String
    suspend fun click(selector: String): String
}
