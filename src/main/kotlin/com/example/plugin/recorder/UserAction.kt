package com.example.plugin.recorder

data class UserAction(
    val actionType: ActionType,
    val target: String,
    val description: String,
    val timestamp: Long,
    val additionalData: Map<String, Any>? = null
)
