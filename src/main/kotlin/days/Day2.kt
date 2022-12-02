package days

import days.Day2.Outcome.LOSE
import days.Day2.Outcome.DRAW
import days.Day2.Outcome.WIN
import days.Day2.Shape.ROCK
import days.Day2.Shape.PAPER
import days.Day2.Shape.SCISSORS

class Day2 : Day(2) {

    private val mappings = mapOf("A" to 1, "X" to 1, "B" to 2, "Y" to 2, "C" to 3, "Z" to 3)

    private val game = inputList.map { it.split(" ").map { mappings[it]!! } }

    override fun partOne(): Any {
        return game.map { Game(Shape.values()[it[0] - 1], Shape.values()[it[1] - 1], null) }
            .sumOf { it.getValue() }
    }

    override fun partTwo(): Any {
        return game.map { Game(Shape.values()[it[0] - 1], null, Outcome.values()[it[1] - 1]) }
            .sumOf { it.getValue() }
    }

    class Game(private val them: Shape, private val us: Shape?, private val outcome: Outcome?) {

        fun getValue(): Int {
            return if (us != null) getOutcome(them, us).value + us.value else getUs(them, outcome!!).value + outcome.value
        }

        private fun getOutcome(them: Shape, us: Shape): Outcome {
            return if ((us == ROCK && them == SCISSORS) || (us == SCISSORS && them == PAPER) || (us == PAPER && them == ROCK)) WIN
            else if (us == them) DRAW
            else LOSE
        }

        private fun getUs(them: Shape, outcome: Outcome): Shape {
            return when (outcome) {
                LOSE -> when (them) { ROCK -> SCISSORS; PAPER -> ROCK; SCISSORS -> PAPER }
                WIN -> when (them) { ROCK -> PAPER; PAPER -> SCISSORS; SCISSORS -> ROCK }
                DRAW -> them
            }
        }
    }

    enum class Shape(val value: Int) { ROCK(1), PAPER(2), SCISSORS(3) }

    enum class Outcome(val value: Int) { LOSE(0), DRAW(3), WIN(6) }
}
