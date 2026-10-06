package com.minicodex.core.ai

import kotlinx.coroutines.flow.Flow

/**
 * Abstract interface for AI providers.
 * Supports various LLM backends (OpenAI, Anthropic, Local).
 */
interface ChatProvider {
    suspend fun generateResponse(prompt: String, tools: List<ToolDefinition>): AIResponse
    fun streamResponse(prompt: String, tools: List<ToolDefinition>): Flow<AIResponseChunk>
}

data class ToolDefinition(
    val name: String,
    val description: String,
    val parameters: ToolParameters
)

data class ToolParameters(
    val type: String, // e.g., "object"
    val properties: Map<String, ParameterProperty>
)

data class ParameterProperty(
    val type: String,
    val description: String
)

data class AIResponse(
    val content: String?,
    val toolCall: ToolCall?
)

data class ToolCall(
    val id: String,
    val toolName: String,
    val arguments: Map<String, Any>
)

data class AIResponseChunk(
    val delta: String?,
    val toolCallChunk: ToolCallChunk?
)

data class ToolCallChunk(
    val index: Int,
    val argumentsDelta: String
)
