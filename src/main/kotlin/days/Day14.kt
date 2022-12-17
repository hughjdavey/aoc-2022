package days

import xyz.hughjd.aocutils.Coords.Coord

class Day14 : Day(14) {

    private val lines = inputList.map { it.split(" -> ")
        .map { it.split(",").map { it.toInt() } }
        .map { Coord(it[0], it[1]) } }

    override fun partOne(): Any {
        val cave = Cave(lines)
        return generateSequence(1) { it + 1 }.takeWhile { cave.addSand() }.count()
    }

    override fun partTwo(): Any {
        val infiniteLine = (-700..700).map { Coord(it, lines.flatten().maxOf { it.y } + 2) }
        val cave = Cave(lines.plusElement(infiniteLine))
        return generateSequence(1) { it + 1 }.takeWhile { cave.addSand() }.count()
    }

    class Cave(lines: List<List<Coord>>) {

        private val bounds = bounds(lines)

        private val cave = (0..bounds.maxY).map { y ->
            (bounds.minX..bounds.maxX).map { x ->
                Coord(x, y) to "."
        }.toMutableList() }.toMutableList()

        private val filledLines = lines.map { it.windowed(2).map {
            val (src, dest) = it[0] to it[1]
            src.coordsTo(dest)
        }.flatten() }.flatten()

        init {
            filledLines.forEach { rock -> set(rock.x, rock.y, "#") }
            set(SAND_ORIGIN.x, SAND_ORIGIN.y, "+")
        }

        fun addSand(): Boolean {
            if (get(SAND_ORIGIN.x, SAND_ORIGIN.y).second == "o") {
                return false
            }

            val finalPosition = generateSequence(SAND_ORIGIN) {
                if (get(it.x, it.y + 1).second == ".") {
                    Coord(it.x, it.y + 1)
                }
                else if (get(it.x - 1, it.y + 1).second == ".") {
                    Coord(it.x - 1, it.y + 1)
                }
                else if (get(it.x + 1, it.y + 1).second == ".") {
                    Coord(it.x + 1, it.y + 1)
                }
                else null
            }.takeWhile { it.y < bounds.maxY + 2 }.last()

            if (finalPosition.x >= bounds.minX && finalPosition.x <= bounds.maxX && finalPosition.y <= bounds.maxY) {
                set(finalPosition.x, finalPosition.y, "o")
                return true
            }
            return false
        }

        fun get(x: Int, y: Int): Pair<Coord, String> {
            if (outOfBounds(x, y)) {
                return Coord(x, y) to "."
            }
            return cave[y][x - bounds.minX]
        }

        fun set(x: Int, y: Int, value: String) {
            cave[y][x - bounds.minX] = get(x, y).first to value
        }

        fun set(x: Int, y: Int, value: Pair<Coord, String>) {
            cave[y][x - bounds.minX] = value
        }

        fun print() {
            System.err.println()
            val topAxisHeight = bounds.maxX.toString().length
            val xAxisLines = (0 until topAxisHeight).joinToString("\n") { y ->
                "  " + (bounds.minX..bounds.maxX).map { r -> r.toString()[y] }.joinToString("")
            }
            System.err.println(xAxisLines)
            (0 until cave.size).forEach { y ->
                System.err.print("$y ")
                (0 until cave[0].size).forEach { x ->
                    System.err.print(cave[y][x].second)
                }
                System.err.println()
            }
        }

        private fun bounds(lines: List<List<Coord>>): GridLimits {
            val flat = lines.flatten()
            return GridLimits(
                flat.minBy { it.x }.x,
                flat.minBy { it.y }.y,
                flat.maxBy { it.x }.x,
                flat.maxBy { it.y }.y,
            )
        }

        private fun outOfBounds(x: Int, y: Int): Boolean {
            return x < bounds.minX || x > bounds.maxX || y < 0 || y > bounds.maxY
        }

        companion object {

            val SAND_ORIGIN = Coord(500, 0)
        }
    }

    data class GridLimits(val minX: Int, val minY: Int, val maxX: Int, val maxY: Int)

}

fun Coord.coordsTo(other: Coord): List<Coord> {
    val diff = other.diff(this)
    if (listOf(diff.x, diff.y).all { it != 0 }) {
        throw IllegalArgumentException("Target Coord must be on same x or y axis as this Coord (this: $this, target: $other)")
    }

    val xAxis = diff.y == 0
    val range = when {
        xAxis && diff.x < 0 -> diff.x..0
        xAxis && diff.x > 0 -> 0..diff.x
        !xAxis && diff.y < 0 -> diff.y..0
        else -> 0..diff.y
    }
    return range.map { d -> if (xAxis) this.copy(x = x + d) else this.copy(y = y + d) }
}
