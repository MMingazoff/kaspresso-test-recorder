package com.example.plugin

import com.intellij.openapi.ui.SimpleToolWindowPanel
import javax.swing.JButton
import javax.swing.JPanel
import javax.swing.JList
import javax.swing.DefaultListModel
import javax.swing.JScrollPane
import java.awt.BorderLayout

class TestRecordingPanel : SimpleToolWindowPanel(true, true) {

    private val startButton: JButton = JButton("Start Test Recording")
    private val finishButton: JButton = JButton("Finish")
    private val actionListModel: DefaultListModel<String> = DefaultListModel()
    private val actionList: JList<String> = JList(actionListModel)

    init {
        val controlPanel = JPanel().apply {
            layout = BorderLayout()
            add(startButton, BorderLayout.WEST)
            add(finishButton, BorderLayout.EAST)
        }

        val actionsPanel = JScrollPane(actionList)

        layout = BorderLayout()
        add(controlPanel, BorderLayout.NORTH)
        add(actionsPanel, BorderLayout.CENTER)
        
        finishButton.isEnabled = false
    }

    fun setOnStartListener(listener: () -> Unit) {
        startButton.addActionListener {
            listener()
            startButton.isEnabled = false
            finishButton.isEnabled = true
        }
    }

    fun setOnFinishListener(listener: () -> Unit) {
        finishButton.addActionListener {
            listener()
            startButton.isEnabled = true
            finishButton.isEnabled = false
        }
    }

    fun addRecordedAction(actionDescription: String) {
        actionListModel.addElement(actionDescription)
    }

    fun clearRecordedActions() {
        actionListModel.clear()
    }
}
