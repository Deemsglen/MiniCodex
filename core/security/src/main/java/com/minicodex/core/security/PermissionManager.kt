package com.minicodex.core.security

/**
 * Security level for AI tool calls.
 */
enum class PermissionLevel {
    SAFE,              // Executes immediately
    REVIEW_REQUIRED,   // Triggers user confirmation dialog
    RESTRICTED        // Blocked unless Developer Mode is enabled
}

/**
 * Interface for managing tool permissions.
 */
interface PermissionManager {
    fun getPermissionLevel(toolName: String): PermissionLevel
    suspend fun requestPermission(toolName: String, arguments: Map<String, Any>): Boolean
}
