package com.minicodex.feature.agent

import com.minicodex.feature.editor.ProjectManager
import com.minicodex.feature.terminal.TerminalEngine
import com.minicodex.feature.browser.BrowserController
import android.webkit.WebView

class MiniCodexToolDispatcherImpl(
    private val projectManager: ProjectManager,
    private val terminalEngine: TerminalEngine,
    private val browserController: BrowserController?
) : ToolDispatcher {

    override fun getDefinitions(): List<ToolDefinition> = listOf(
        ToolDefinition(" file_read\, \Read contents of a file\,
 ToolParameters(\object\, mapOf(\path\ to ParameterProperty(\string\, \Path to file\)))),
 ToolDefinition(\file_write\, \Write content to a file\,
 ToolParameters(\object\, mapOf(\path\ to ParameterProperty(\string\, \Path to file\), \content\ to ParameterProperty(\string\, \Content to write\)))),
 ToolDefinition(\file_delete\, \Delete a file or folder\,
 ToolParameters(\object\, mapOf(\path\ to ParameterProperty(\string\, \Path to delete\)))),
 ToolDefinition(\directory_list\, \List files in a directory\,
 ToolParameters(\object\, mapOf(\path\ to ParameterProperty(\string\, \Path to list\)))),
 ToolDefinition(\terminal_execute\, \Execute a shell command\,
 ToolParameters(\object\, mapOf(\command\ to ParameterProperty(\string\, \Command to run\)))),
 ToolDefinition(\browser_open\, \Open a URL in browser\,
 ToolParameters(\object\, mapOf(\url\ to ParameterProperty(\string\, \URL to open\)))),
 ToolDefinition(\browser_click\, \Click an element on page\,
 ToolParameters(\object\, mapOf(\selector\ to ParameterProperty(\string\, \CSS selector\)))),
 ToolDefinition(\browser_read\, \Read page content\,
 ToolParameters(\object\, mapOf(\selector\ to ParameterProperty(\string\, \CSS selector\))))
 )

 override suspend fun dispatch(toolCall: ToolCall): String {
 return try {
 when (toolCall.toolName) {
 \file_read\ -> projectManager.readFile(toolCall.arguments[\path\] as String)
 \file_write\ -> projectManager.writeFile(toolCall.arguments[\path\] as String, toolCall.arguments[\content\] as String)
 \file_delete\ -> projectManager.deleteFile(toolCall.arguments[\path\] as String)
 \directory_list\ -> projectManager.listFiles(toolCall.arguments[\path\] as? String ?: \\).joinToString(\\\n\) { it.name }
 \terminal_execute\ -> terminalEngine.executeCommand(toolCall.arguments[\command\] as String)
 \browser_open\ -> {
 browserController?.openUrl(toolCall.arguments[\url\] as String)
 \Opened URL: \
 }
 \browser_click\ -> browserController?.clickElement(toolCall.arguments[\selector\] as String) ?: \Browser not initialized\
 \browser_read\ -> browserController?.readElement(toolCall.arguments[\selector\] as String) ?: \Browser not initialized\
 else -> \Error: Unknown tool \
 }
 } catch (e: Exception) {
 \Tool Execution Error: \
 }
 }
}
