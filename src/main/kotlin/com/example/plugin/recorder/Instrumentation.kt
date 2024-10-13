package com.example.plugin.recorder

interface Instrumentation {

    fun addMonitor(name: String, callback: (Event) -> Unit)
    fun removeMonitor(name: String)
}

sealed class Event(
    open val id: String,
    open val action: String,
) {

    class KeyEvent(
        override val id: String,
        override val action: String,
        val enteredText: String
    ) : Event(id, action)

    class MotionEvent(
        override val id: String,
        override val action: String,
    ) : Event(id, action)

    fun getTargetElementId() = id

    companion object {
        const val ACTION_UP = "ACTION_UP"
        const val ACTION_DOWN = "ACTION_DOWN"
    }
}
