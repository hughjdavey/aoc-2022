package days

import xyz.hughjd.aocutils.Collections.split
import java.util.Stack

class Day5 : Day(5) {

    private val inputs = inputList.split("")

    private val moves = getMoves(inputs[1])

    override fun partOne(): Any {
        return moveStacks(this::move9000)
    }

    override fun partTwo(): Any {
        return moveStacks(this::move9001)
    }

    private fun moveStacks(moveFn: (move: Move, stacks: List<Stack<Char>>) -> Unit): String {
        val stacks = getStacks(inputs[0])
        moves.forEach { moveFn(it, stacks) }
        return stacks.map(Stack<Char>::pop).joinToString("")
    }

    data class Move(val n: Int, val src: Int, val dest: Int)

    fun move9000(move: Move, stacks: List<Stack<Char>>) {
        (0 until move.n).forEach { stacks[move.dest - 1].push(stacks[move.src - 1].pop()) }
    }

    fun move9001(move: Move, stacks: List<Stack<Char>>) {
        val toMove = (0 until move.n).map { stacks[move.src - 1].pop() }.reversed()
        toMove.forEach { stacks[move.dest - 1].push(it) }
    }

    companion object {

        fun getStacks(stacksInput: List<String>): List<Stack<Char>> {
            val maxLen = stacksInput.maxBy { it.length }.length
            val range = (1..maxLen).step(4)
            val stacks = range.map { Stack<Char>() }
            return stacksInput.dropLast(1).foldRight(stacks) { elem, acc ->
                val cols = range.map { index -> getLetterOrNull(elem, index) }
                cols.forEachIndexed { index, c -> if (c != null) acc[index].push(c) }
                acc
            }
        }

        fun getMoves(movesInput: List<String>): List<Move> {
            return movesInput.map { move ->
                val matches = Regex(".+?(\\d+).+?(\\d+).+?(\\d+)").matchEntire(move)!!.groupValues.drop(1)
                matches.map { it.toInt() }
            }.map { Move(it[0], it[1], it[2]) }
        }

        private fun getLetterOrNull(str: String, index: Int): Char? {
            val char = str.getOrNull(index)
            return if (char == null) null else if (Character.isAlphabetic(char.code)) char else null
        }
    }
}
