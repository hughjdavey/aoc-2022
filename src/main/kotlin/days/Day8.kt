package days

import xyz.hughjd.aocutils.Collections.takeWhileInclusive
import xyz.hughjd.aocutils.Coords.Coord

class Day8 : Day(8) {

    private val trees = Trees(inputList)

    private val treeCoords = trees.trees.indices.flatMap { y ->
        trees.trees[0].indices.map { x ->
            Coord(x, y)
        }
    }

    override fun partOne(): Any {
        return treeCoords.count(trees::isVisible)
    }

    override fun partTwo(): Any {
        return treeCoords.map(trees::scenicScore).max()
    }

    class Trees(input: List<String>, val trees: List<List<Int>> = getTrees(input)) {

        fun scenicScore(location: Coord): Int {
            return getTreesToEdgeOfGrid(location, true).map { it.size }.fold(1) { acc, elem -> acc * elem }
        }

        fun isVisible(location: Coord): Boolean {
            val height = safeGet(location)!!
            return getTreesToEdgeOfGrid(location).any { it.all { height2 -> height2 < height } }
        }

        fun getTreesToEdgeOfGrid(location: Coord, considerView: Boolean = false): List<List<Int>> {
            val height = if (considerView) safeGet(location)!! else null
            return listOf(shiftToEdge(location, height) { it.plusX(1) }, shiftToEdge(location, height) { it.minusX(1) },
                shiftToEdge(location, height) { it.plusY(1) }, shiftToEdge(location, height) { it.minusY(1) })
        }

        private fun shiftToEdge(location: Coord, maxHeight: Int?, shift: (c: Coord) -> Coord): List<Int> {
            return generateSequence(location, shift).map(this::safeGet).drop(1)
                .takeWhileInclusive { it != null && (maxHeight == null || it < maxHeight) }
                .filterNotNull().toList()
        }

        private fun safeGet(location: Coord): Int? {
            return try {
                trees[location.y][location.x]
            } catch (e: Exception) {
                null
            }
        }

        companion object {

            fun getTrees(input: List<String>): List<List<Int>> {
                return input.fold(listOf()) { acc, elem ->
                    val row: List<Int> = elem.toCharArray().map { it.toString().toInt() }
                    acc.plusElement(row)
                }
            }
        }
    }
}
