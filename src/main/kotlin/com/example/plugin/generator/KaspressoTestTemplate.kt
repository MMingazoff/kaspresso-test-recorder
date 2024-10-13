package com.example.plugin.generator

class KaspressoTestTemplate {

    fun getHeader(): String {
        return """
            |import androidx.test.ext.junit.rules.ActivityScenarioRule
            |import androidx.test.ext.junit.runners.AndroidJUnit4
            |import com.kaspersky.kaspresso.kaspresso.Kaspresso
            |import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
            |import org.junit.Rule
            |import org.junit.Test
            |import org.junit.runner.RunWith
            |
            |@RunWith(AndroidJUnit4::class)
            |class GeneratedTest : TestCase(Kaspresso.Builder.default()) {
            |
            |    @get:Rule
            |    val activityRule = ActivityScenarioRule(MainActivity::class.java)
            |
            |    @Test
            |    fun testGeneratedActions() = run {
            |        step("Start test") {
            |            // Начало теста
            |        }
        """.trimMargin()
    }

    fun getFooter(): String {
        return """
            |        step("End test") {
            |            // Завершение теста
            |        }
            |    }
            |}
        """.trimMargin()
    }
}
