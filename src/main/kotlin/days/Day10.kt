package days

import xyz.hughjd.aocutils.Collections.stackOf
import java.util.Stack

class Day10 : Day(10) {

    override fun partOne(): Any {
        return CPU().run(getProgram(), listOf(20, 60, 100, 140, 180, 220)).sumOf { it.strength }
    }

    override fun partTwo(): Any {
        return "\n${CRT().drawScreen(getProgram())}"
    }

    private fun getProgram(): List<Instruction> {
        return inputList.map {
            if (it == "noop") Noop() else {
                val parts = it.split(" ")
                AddX(parts[1].toInt())
            }
        }
    }

    data class CRT(private val pxHeight: Int = 6, private val pxWidth: Int = 40, private val pxTotal: Int = pxHeight * pxWidth) {

        fun drawScreen(program: List<Instruction>): String {
            val states = CPU().run(program, List(pxTotal) { index -> index + 1 })
            return (0 until pxTotal).map { pixel ->
                val state = states[pixel]
                val spritePixels = listOf(state.x - 1, state.x, state.x + 1)
                if (pixel % 40 in spritePixels) "#" else "."
            }.chunked(40).joinToString("\n") { it.joinToString("") }
        }
    }

    sealed class Instruction(var cycles: Int)

    class Noop : Instruction(1)

    data class AddX(val v: Int) : Instruction(2)

    data class SignalStrength(val cycle: Int, val x: Int, val strength: Int = cycle * x)

    data class CPU(private val debug: Boolean = false) {

        private var x: Int = 1
        private var cycle = 0
        private lateinit var instructions: Stack<Instruction>
        private var currentInstruction: Instruction? = null

        private var done: Boolean = false

        fun run(program: List<Instruction>, signalStrengthRequests: List<Int> = emptyList()): List<SignalStrength> {
            reset(program)
            val strengths = mutableListOf<SignalStrength>()
            while (!done) {
                if (cycle in signalStrengthRequests) {
                    strengths.add(SignalStrength(cycle, x))
                }
                cycle()
            }
            return strengths
        }

        fun getX(): Int {
            return x
        }

        private fun cycle() {
            if (currentInstruction == null) {
                if (instructions.empty()) {
                    debugLog("No more instructions - ending execution")
                    done = true
                    return
                }
                currentInstruction = instructions.pop()
                debugLog("    New instruction is $currentInstruction")
            }

            val instruction = currentInstruction!!
            cycle++
            debugLog("=== Cycle #$cycle ===")
            debugLog("Current instruction is $instruction with ${instruction.cycles} cycles remaining")

            if (instruction.cycles == 1) {
                debugLog("  Running $instruction")
                runInstruction(instruction)
            }

            instruction.cycles--

            if (instruction.cycles == 0) {
                debugLog("    $instruction complete")
                currentInstruction = null
            }

            debugLog("After cycle $cycle, register X is $x")
        }

        private fun debugLog(message: String) {
            if (debug) {
                System.err.println(message)
            }
        }

        private fun reset(program: List<Instruction>) {
            x = 1
            cycle = 1
            instructions = stackOf(program)
            currentInstruction = instructions.pop()
            done = false
        }

        private fun runInstruction(instruction: Instruction) {
            when (instruction) {
                is AddX -> x += instruction.v
                is Noop -> {}
            }
        }

    }
}
