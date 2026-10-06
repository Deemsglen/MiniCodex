package com.minicodex.feature.agent

import com.minicodex.core.ai.*
import com.minicodex.core.security.PermissionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * The core agent engine that manages the AI thought-action loop.
 */
class AgentEngine(
    private val chatProvider: ChatProvider,
    private val toolDispatcher: ToolDispatcher,
    private val permissionManager: PermissionManager
) {
    private val _agentState = MutableStateFlow<AgentStatus>(AgentStatus.Idle)
    val agentState = _agentState.asStateFlow()

    private val _activityLog = MutableStateFlow<List<AgentActivity>>(emptyList())
    val activityLog = _activityLog.asStateFlow()

    suspend fun executeTask(userPrompt: String) {
        _agentState.value = AgentStatus.Planning
        logActivity("🤖 Agent started: $userPrompt")

        var currentPrompt = userPrompt
        var isTaskComplete = false

        while (!isTaskComplete) {
            val response = chatProvider.generateResponse(currentPrompt, toolDispatcher.getDefinitions())

            if (response.toolCall != null) {
                val toolCall = response.toolCall
                val level = permissionManager.getPermissionLevel(toolCall.toolName)

                if (level == PermissionLevel.RESTRICTED) {
                    logActivity("❌ Tool ${toolCall.toolName} is restricted.")
                    currentPrompt = "Error: Tool ${toolCall.toolName} is restricted on this device."
                    continue
                }

                if (level == PermissionLevel.REVIEW_REQUIRED) {
                    val allowed = permissionManager.requestPermission(toolCall.toolName, toolCall.arguments)
                    if (!allowed) {
                        logActivity("🚫 User denied permission for ${toolCall.toolName}")
                        currentPrompt = "User denied permission for tool ${toolCall.toolName}."
                        continue
                    }
                }

                logActivity("🛠️ Executing ${toolCall.toolName}...")
                val result = toolDispatcher.dispatch(toolCall)
                logActivity("✅ Result: $result")
                currentPrompt = "Tool ${toolCall.toolName} returned: $result"
            } else {
                logActivity("🏁 Task completed.")
                _agentState.value = AgentStatus.Idle
                isTaskComplete = true
            }
        }
    }

    private fun logActivity(message: String) {
        _activityLog.value = _activityLog.value + AgentActivity(message)
    }
}

sealed class AgentStatus {
    object Idle : AgentStatus()
    object Planning : AgentStatus()
    object Executing : AgentStatus()
    object Fixing : AgentStatus()
}

data class AgentActivity(val message: String, val timestamp: Long = System.currentTimeMillis())
