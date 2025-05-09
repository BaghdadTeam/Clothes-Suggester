package org.baghdad.presentation.input

class CliReader: Reader {
    override fun readInput(): String {
        return readln()
    }
}