package days

import xyz.hughjd.aocutils.Coords.Coord
import java.util.LinkedList
import java.util.Queue

class Day12 : Day(12) {

    private val pointsInfo = PointsInfo.fromInput(inputList)

    override fun partOne(): Any {
        return dijkstra(pointsInfo)[pointsInfo.end] ?: 0
    }

    // todo improve performance - takes ~2 mins
    override fun partTwo(): Any {
        val possibleStarts = pointsInfo.points.filter { it.letter == 'a' }
        return possibleStarts.parallelStream().map { start -> pointsInfo.copy(start = start.position) }
            .map { dijkstra(it)[it.end] ?: 0 }.filter { it > 0 }.min(Int::compareTo).orElse(0)
    }

    // see https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm#Pseudocode
    private fun dijkstra(pointsInfo: PointsInfo): MutableMap<Coord, Int> {
        val dist = mutableMapOf<Coord, Int>()
        val Q = LinkedList<Point>()

        pointsInfo.points.forEach { point ->
            dist[point.position] = Int.MAX_VALUE
            Q.add(point)
        }
        dist[pointsInfo.start] = 0

        while (Q.isNotEmpty()) {
            val u = Q.minBy { dist[it.position]!! }
            Q.remove(u)

            u.neighboursInQueue(Q).forEach { v ->
                val alt = dist[u.position]!! + 1
                if (alt < dist[v.position]!! && v.elevation - u.elevation <= 1) {
                    dist[v.position] = alt
                }
            }
        }
        return dist
    }

    data class PointsInfo(val start: Coord, val end: Coord, val points: List<Point>) {

        companion object {

            fun fromInput(input: List<String>): PointsInfo {
                lateinit var start: Coord
                lateinit var end: Coord
                val points = input.mapIndexed { y, str ->
                    str.mapIndexed { x, char ->
                        if (char == 'S') {
                            start = Coord(x, y)
                        }
                        else if (char == 'E') {
                            end = Coord(x, y)
                        }
                        Point(Coord(x, y), char)
                    }
                }.flatten()
                return PointsInfo(start, end, points)
            }
        }
    }

    data class Point(val position: Coord, val letter: Char, val elevation: Int = getElevation(letter)) {

        fun neighboursInQueue(queue: Queue<Point>): List<Point> {
            return position.getAdjacent(false)
                .mapNotNull { c -> queue.find { it.position == c } }
        }

        companion object {

            fun getElevation(letter: Char) = when (letter) {
                'S' -> 0
                'E' -> 25
                else -> letter.code - 97
            }
        }
    }
}
