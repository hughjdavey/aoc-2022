package days

import xyz.hughjd.aocutils.Collections.stackOf
import xyz.hughjd.aocutils.Tuples.product

class Day10 : Day(10) {

    val program = inputList.map {
        if (it == "noop") Noop() else {
            val parts = it.split(" ")
            AddX(v = parts[1].toInt())
        }
    }

    override fun partOne(): Any {
        return CPU().run(program)
            .filter { it.first in listOf(20, 60, 100, 140, 180, 220) }
            .sumOf { it.product() }
    }

    override fun partTwo(): Any {
        return "\n${CRT().drawScreen(program)}"
    }

    data class CRT(private val pxHeight: Int = 6, private val pxWidth: Int = 40) {

        private val pxTotal: Int = pxHeight * pxWidth

        fun drawScreen(program: List<Instruction>): String {
            val states = CPU().run(program)
            return (0 until pxTotal).map { pixel ->
                val x = states[pixel].second
                val spritePixels = listOf(x - 1, x, x + 1)
                if (pixel % 40 in spritePixels) "#" else "."
            }.chunked(pxWidth).joinToString("\n") { it.joinToString("") }
        }
    }

    sealed class Instruction {
        abstract val cycles: Int
        abstract fun exec(): Instruction
    }

    data class Noop(override val cycles: Int = 1) : Instruction() {
        override fun exec() = copy(cycles = cycles - 1)
    }

    data class AddX(override val cycles: Int = 2, val v: Int) : Instruction() {
        override fun exec() = copy(cycles = cycles - 1)
    }

    // minor issue with this class is that because of the `takeWhile { !instructions.empty() }`, execution ends
    // before the program is done (because the stack is empty but the current instruction may have 1 or 2 cycles left)
    // workaround for now is adding as many extra Noops as the final instruction has cycles at the end which ensure the 'real' instructions get fully executed
    class CPU {

        fun run(program: List<Instruction>): List<Pair<Int, Int>> {
            val instructions = stackOf(program + List(program.last().cycles) { Noop() })
            return generateSequence(Triple(1, 1, instructions.pop())) { (cycle, x, instruction) ->
                if (instruction.cycles == 1) {
                    Triple(cycle + 1, runInstruction(instruction, x), instructions.pop())
                }
                else {
                    Triple(cycle + 1, x, instruction.exec())
                }
            }.map { it.first to it.second }.takeWhile { !instructions.empty() }.toList()
        }

        private fun runInstruction(instruction: Instruction, x: Int): Int {
            return when (instruction) {
                is AddX -> x + instruction.v
                is Noop -> x
            }
        }
    }
}
