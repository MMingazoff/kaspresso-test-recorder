package com.example.plugin.utils

import java.io.File
import java.io.IOException

class FileSaver(private val testDirectoryPath: String) {

    fun saveTestFile(testCode: String, fileName: String = "GeneratedTest.kt") {
        try {
            val testDirectory = File(testDirectoryPath)
            
            if (!testDirectory.exists()) {
                testDirectory.mkdirs()
            }

            val testFile = File(testDirectory, fileName)
            testFile.writeText(testCode)

            println("Test file saved successfully at ${testFile.absolutePath}")
        } catch (e: IOException) {
            println("Failed to save test file: ${e.message}")
        }
    }
}
