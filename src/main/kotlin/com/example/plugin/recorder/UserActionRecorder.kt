package com.example.plugin.recorder

class UserActionRecorder {

    private val recordedActions = mutableListOf<UserAction>()

    private var onActionRecorded: ((UserAction) -> Unit)? = null

    fun startRecording(onActionRecorded: (UserAction) -> Unit) {
        this.onActionRecorded = onActionRecorded
        recordedActions.clear()
        log("User action recording started.")
    }

    fun stopRecording() {
        this.onActionRecorded = null
        log("User action recording stopped.")
    }

    fun getRecordedActions(): List<UserAction> {
        return recordedActions.toList()
    }

    fun recordAction(actionType: ActionType, target: String, description: String, additionalData: Map<String, Any>? = null) {
        val action = UserAction(
            actionType = actionType,
            target = target,
            description = description,
            timestamp = System.currentTimeMillis(),
            additionalData = additionalData
        )
        recordedActions.add(action)
        onActionRecorded?.invoke(action)
        log("Recorded action: $description")
    }

    private fun log(message: String) {
        println(message)
    }
}
