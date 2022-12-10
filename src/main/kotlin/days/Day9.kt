package days

import days.Day9.Direction.DOWN
import days.Day9.Direction.LEFT
import days.Day9.Direction.RIGHT
import days.Day9.Direction.UP
import days.Day9.LongRope.Companion.INITIAL_COORDS
import xyz.hughjd.aocutils.Coords.Coord

class Day9 : Day(9) {

    private val motions = inputList.map { it.split(" ") }
        .map { (dir, n) -> Direction.values().find { it.toString()[0] == dir[0] }!! to n.toInt() }

    override fun partOne(): Any {
        return motions.fold(listOf<Rope>()) { ropes, (dir, n) -> ropes + (ropes.lastOrNull() ?: Rope.INITIAL).move(dir, n) }
            .map { it.tail }.toSet().size
    }

    override fun partTwo(): Any {
        return partTwoImpl(motions).first
    }

    // expose this for testing
    fun partTwoTest(input: List<String> = inputList): Pair<Int, List<String>> {
        val motions = input.map { it.split(" ") }
            .map { (dir, n) -> Direction.values().find { it.toString()[0] == dir[0] }!! to n.toInt() }
        return partTwoImpl(motions)
    }

    // todo refactor/simplify this method
    private fun partTwoImpl(motions: List<Pair<Direction, Int>>): Pair<Int, List<String>> {
        val coordStates = motions.fold(mutableListOf(INITIAL_COORDS)) { coords2, (dir, n) ->
            (0 until n).forEach {
                coords2.add(LongRope(coords2.last()).move(dir))
            }
            coords2
        }
        return coordStates.map { it.last() }.toSet().size to coordStates.map { LongRope(it).getPrintableState() }
    }

    enum class Direction { UP, DOWN, RIGHT, LEFT }

    // todo refactor/simplify this class
    data class LongRope(private val knots: List<Coord>) {

        fun move(direction: Direction): List<Coord> {
            val coords = knots.toMutableList()
            coords[0] = when (direction) {
                UP -> coords[0].minusY(1)
                DOWN -> coords[0].plusY(1)
                RIGHT -> coords[0].plusX(1)
                LEFT -> coords[0].minusX(1)
            }
            for (i in 0 until coords.lastIndex) {
                val rope = Rope(coords[i], coords[i + 1])
                val tail = if (rope.touching()) coords[i + 1] else Rope.tailMove(Rope(coords[i], coords[i + 1]))
                if (coords[i + 1] != tail) {
                    coords[i + 1] = tail
                }
            }
            return coords.toList()
        }

        fun getPrintableState(lowY: Int = -4, highX: Int = 5): String {
            return (lowY..0).map { y ->
                (0..highX).map { x ->
                    val atSpot = knots.mapIndexedNotNull { index, coord -> if (coord == Coord(x, y)) index else null }
                    atSpot.firstOrNull() ?: "."
                }.joinToString("")
            }.joinToString("\n")
        }

        companion object {

            val INITIAL_COORDS = listOf(
                Coord(0, 0), Coord(0, 0),
                Coord(0, 0), Coord(0, 0),
                Coord(0, 0), Coord(0, 0),
                Coord(0, 0), Coord(0, 0),
                Coord(0, 0), Coord(0, 0),
            )
        }
    }

    data class Rope(val head: Coord, val tail: Coord) {

        fun touching(): Boolean {
            return head == tail || tail in head.getAdjacent(true)
        }

        fun move(direction: Direction, n: Int): List<Rope> {
            return (0 until n).fold(listOf(this)) { ropes, _ ->
                ropes + ropes.last().move(direction)
            }.drop(1)
        }

        private fun move(direction: Direction): Rope {
            val afterHeadMoved = copy(head = when (direction) {
                UP -> head.minusY(1)
                DOWN -> head.plusY(1)
                RIGHT -> head.plusX(1)
                LEFT -> head.minusX(1)
            })
            return if (afterHeadMoved.touching()) afterHeadMoved else afterHeadMoved.copy(tail = tailMove(afterHeadMoved))
        }

        companion object {

            val INITIAL = Rope(Coord(0, 0), Coord(0, 0))

            // todo surely a better way of doing this
            fun tailMove(rope: Rope): Coord {
                val diff = rope.head.diff(rope.tail)
                return when {
                    diff.x == 2 && diff.y == 0 -> rope.tail.copy(x = rope.tail.x + 1)
                    diff.x == -2 && diff.y == 0 -> rope.tail.copy(x = rope.tail.x - 1)
                    diff.y == 2 && diff.x == 0 -> rope.tail.copy(y = rope.tail.y + 1)
                    diff.y == -2 && diff.x == 0 -> rope.tail.copy(y = rope.tail.y - 1)

                    diff.x == 2 && diff.y == 1 -> rope.tail.copy(x = rope.tail.x + 1, rope.tail.y + 1)
                    diff.x == 2 && diff.y == -1 -> rope.tail.copy(x = rope.tail.x +1, rope.tail.y - 1)
                    diff.x == -2 && diff.y == 1 -> rope.tail.copy(x = rope.tail.x - 1, rope.tail.y + 1)
                    diff.x == -2 && diff.y == -1 -> rope.tail.copy(x = rope.tail.x - 1, rope.tail.y - 1)
                    diff.y == 2 && diff.x == 1 -> rope.tail.copy(x = rope.tail.x + 1, rope.tail.y + 1)
                    diff.y == 2 && diff.x == -1 -> rope.tail.copy(x = rope.tail.x - 1, rope.tail.y + 1)
                    diff.y == -2 && diff.x == 1 -> rope.tail.copy(x = rope.tail.x + 1, rope.tail.y - 1)
                    diff.y == -2 && diff.x == -1 -> rope.tail.copy(x = rope.tail.x - 1, rope.tail.y - 1)

                    diff.x == 2 && diff.y == -2 -> rope.tail.copy(x = rope.tail.x + 1, rope.tail.y - 1)
                    diff.x == 2 && diff.y == 2 -> rope.tail.copy(x = rope.tail.x + 1, rope.tail.y + 1)
                    diff.x == -2 && diff.y == -2 -> rope.tail.copy(x = rope.tail.x - 1, rope.tail.y - 1)
                    diff.x == -2 && diff.y == 2 -> rope.tail.copy(x = rope.tail.x - 1, rope.tail.y + 1)
                    else -> Coord(0, 0)
                }
            }
        }
    }
}
