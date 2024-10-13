package com.example.plugin.generator

import com.example.plugin.recorder.ActionType
import com.example.plugin.recorder.UserAction
import com.example.plugin.utils.FileSaver

class TestGenerator(
    private val template: KaspressoTestTemplate,
    private val fileSaver: FileSaver
) {

    fun generateTest(actions: List<UserAction>) {
        val testCode = StringBuilder()

        testCode.append(template.getHeader())

        for (action in actions) {
            val actionCode = generateActionCode(action)
            testCode.append(actionCode)
        }

        testCode.append(template.getFooter())

        fileSaver.saveTestFile(testCode.toString())
    }

    private fun generateActionCode(action: UserAction): String {
        return when (action.actionType) {
            ActionType.CLICK -> {
                "step(\"${action.description}\") {\n" +
                        "    KButton { withId(\"${action.target}\") }.click()\n" +
                        "}\n"
            }

            ActionType.INPUT_TEXT -> {
                val inputText = action.additionalData?.get("text") ?: ""
                "step(\"${action.description}\") {\n" +
                        "    KEditText { withId(\"${action.target}\") }.typeText(\"$inputText\")\n" +
                        "}\n"
            }

            ActionType.SCROLL -> {
                "step(\"${action.description}\") {\n" +
                        "    KScrollView { withId(\"${action.target}\") }.scrollTo()\n" +
                        "}\n"
            }

            ActionType.SWIPE -> {
                "step(\"${action.description}\") {\n" +
                        "    KView { withId(\"${action.target}\") }.swipe()\n" +
                        "}\n"
            }

            ActionType.LONG_CLICK -> {
                "step(\"${action.description}\") {\n" +
                        "    KButton { withId(\"${action.target}\") }.longClick()\n" +
                        "}\n"
            }

            ActionType.CHECK -> {
                "step(\"${action.description}\") {\n" +
                        "    KCheckBox { withId(\"${action.target}\") }.setChecked(true)\n" +
                        "}\n"
            }

            ActionType.SELECT -> {
                "step(\"${action.description}\") {\n" +
                        "    KSpinner { withId(\"${action.target}\") }.selectItem(0)\n" +
                        "}\n"
            }

            ActionType.BACK_PRESS -> {
                "step(\"${action.description}\") {\n" +
                        "    device.pressBack()\n" +
                        "}\n"
            }

            ActionType.CUSTOM -> {
                "// Custom action for ${action.description} not implemented\n"
            }
        }
    }
}
