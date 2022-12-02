package days

class Day2 : Day(2) {

    private val mappings = mapOf("A" to 1, "X" to 1, "B" to 2, "Y" to 2, "C" to 3, "Z" to 3)

    private val game = inputList.map { it.split(" ").map { mappings[it]!! } }

    override fun partOne(): Any {
        return game.sumOf { it.last() + getOutcome(it) }
    }

    override fun partTwo(): Any {
        return game.map { getShape(it) }.sumOf { it.last() + getOutcome(it) }
    }

    // 1 = rock, 2 = paper, 3 = scissors
    private fun getOutcome(ns: List<Int>): Int {
        val (them, us) = ns[0] to ns[1]
        return when {
            us == 1 && them == 3 -> 6
            us == 3 && them == 2 -> 6
            us == 2 && them == 1 -> 6
            us == them -> 3
            else -> 0
        }
    }

    // 1 = rock, 2 = paper, 3 = scissors
    private fun getShape(ns: List<Int>): List<Int> {
        val (them, outcome) = ns[0] to ns[1]
        val us = when {
            outcome == 1 && them == 1 -> 3
            outcome == 1 && them == 2 -> 1
            outcome == 1 && them == 3 -> 2
            outcome == 3 && them == 1 -> 2
            outcome == 3 && them == 2 -> 3
            outcome == 3 && them == 3 -> 1
            else -> them
        }
        return listOf(them, us)
    }
}
