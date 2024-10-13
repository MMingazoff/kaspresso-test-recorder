package com.example.plugin

import com.intellij.openapi.actionSystem.*
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.VirtualFileEvent
import com.intellij.openapi.vfs.VirtualFileListener
import com.intellij.openapi.vfs.VirtualFileManager

class KaspressoTestRecorderAction : AnAction() {

    private var hasShown = false

    override fun actionPerformed(event: AnActionEvent) {
        val project = event.project ?: return
        startTestRecorder(project)
        VirtualFileManager.getInstance().addVirtualFileListener(object : VirtualFileListener {

            var hasChanged = false

            override fun contentsChanged(event: VirtualFileEvent) {
                if (hasChanged) return
                val file = event.file
                if (file.path.contains("/src/androidTest/") && (file.extension == "kt")) {
                    hasChanged = true
                    processGeneratedTestFile(file, project)
                }
            }
        })
    }

    private fun startTestRecorder(project: Project) {
        val actionManager = ActionManager.getInstance()
        val action = actionManager.getAction("GoogleCloudTesting.TestRecorder")
        if (action != null) {
            val dataContext = DataContext { dataId ->
                when (dataId) {
                    PlatformDataKeys.PROJECT.name -> project
                    else -> null
                }
            }
            val presentation = Presentation().apply {
                text = "Record Espresso Test"
            }
            val event = AnActionEvent.createFromDataContext("", presentation, dataContext)
            action.actionPerformed(event)
            Messages.showInfoMessage("Kaspresso Test Recorder started. Record your test and save it.", "Information")
        } else {
            Messages.showErrorDialog("Error occured", "Error!")
        }
    }

    private fun processGeneratedTestFile(file: VirtualFile, project: Project) {
        if (hasShown) return
        hasShown = true
        Messages.showInfoMessage(
            "Kaspresso test generation process has completed successfully. Test saved to ${file.path}",
            "Generation complete"
        )
    }
}
