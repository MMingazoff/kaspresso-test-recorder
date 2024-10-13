package com.example.plugin.recorder

import com.example.plugin.recorder.Event.*

class InstrumentationHelper(private val instrumentation: Instrumentation) {

    interface ActionListener {
        fun onUserAction(actionType: ActionType, target: String, description: String, additionalData: Map<String, Any>?)
    }

    private var actionListener: ActionListener? = null

    fun setActionListener(listener: ActionListener) {
        this.actionListener = listener
    }

    fun monitorTouchEvents() {
        instrumentation.addMonitor(MotionEvent::class.java.name) { event ->
            if (event.action == Event.ACTION_UP) {
                actionListener?.onUserAction(
                    ActionType.CLICK,
                    target = event.getTargetElementId(),
                    description = "Нажатие на элемент с ID ${event.getTargetElementId()}",
                    additionalData = null
                )
            }
        }
    }

    fun monitorKeyEvents() {
        instrumentation.addMonitor(KeyEvent::class.java.name) { event ->
            if (event.action == Event.ACTION_DOWN) {
                actionListener?.onUserAction(
                    ActionType.INPUT_TEXT,
                    target = event.getTargetElementId(),
                    description = "Ввод текста в элемент с ID ${event.getTargetElementId()}",
                    additionalData = mapOf("text" to (event as KeyEvent).enteredText)
                )
            }
        }
    }

    fun stopMonitoring() {
        instrumentation.removeMonitor(MotionEvent::class.java.name)
        instrumentation.removeMonitor(KeyEvent::class.java.name)
    }
}
