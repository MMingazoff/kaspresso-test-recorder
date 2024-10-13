package com.example.plugin

import com.example.plugin.generator.TestGenerator
import com.example.plugin.recorder.UserAction
import com.example.plugin.recorder.UserActionRecorder

class TestRecordingController(
    private val panel: TestRecordingPanel,
    private val recorder: UserActionRecorder,
    private val generator: TestGenerator
) {

    init {
        setupListeners()
    }

    private fun setupListeners() {
        panel.setOnStartListener {
            startRecording()
        }

        panel.setOnFinishListener {
            finishRecording()
        }
    }

    private fun startRecording() {
        recorder.startRecording { action ->
            onUserActionRecorded(action)
        }
        panel.clearRecordedActions()
        log("Recording started")
    }

    private fun finishRecording() {
        recorder.stopRecording()
        generateTestCode()
        log("Recording finished")
    }

    private fun onUserActionRecorded(action: UserAction) {
        panel.addRecordedAction(action.description)
    }

    private fun generateTestCode() {
        val recordedActions = recorder.getRecordedActions()
        generator.generateTest(recordedActions)
    }

    private fun log(message: String) {
        println(message)
    }
}
