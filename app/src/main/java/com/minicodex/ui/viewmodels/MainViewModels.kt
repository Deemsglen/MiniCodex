package com.minicodex.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import com.minicodex.feature.agent.*
import com.minicodex.feature.terminal.TerminalEngine
import com.minicodex.feature.editor.ProjectManager
import com.minicodex.core.ai.ChatProvider
import com.minicodex.core.security.PermissionManager

class AgentViewModel(
    private val agentEngine: AgentEngine,
    private val projectManager: ProjectManager
) : ViewModel() {
    val agentStatus = agentEngine.agentState
    val activityLog = agentEngine.activityLog

    fun sendInstruction(instruction: String) {
        viewModelScope.launch {
            agentEngine.executeTask(instruction)
        }
    }

    fun stopAgent() {
        // Implementation for stopping the agent loop
    }
}

class TerminalViewModel(
    private val terminalEngine: TerminalEngine
) : ViewModel() {
    private val _output = MutableStateFlow("$ welcome to MiniCodex Terminal\n$ ")
    val output = _output.asStateFlow()

    fun executeCommand(command: String) {
        viewModelScope.launch {
            _output.value += "$ $command\n"
            val result = terminalEngine.executeCommand(command)
            _output.value += "$result\n$ "
        }
    }
}
