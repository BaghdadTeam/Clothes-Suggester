package org.baghdad.presentation.input

import java.util.Scanner


class CliReader : Reader {
    private val scanner = Scanner(System.`in`)

    override fun readInput(): String {
        return scanner.nextLine().trim()
    }

    fun readLocation(): String {
        println("📍 Enter location (city/ZIP): ")
        return readInput()
    }

    fun readMenuChoice(): Int {
        println("➡️ Enter choice (1-5): ")
        return readInput().toIntOrNull() ?: -1
    }

    fun readPreferenceInput(prompt: String): String {
        println(prompt)
        return scanner.nextLine().trim()
    }

    fun waitForEnter() {
        println("\nPress [Enter] to continue...")
        readInput()
    }
}