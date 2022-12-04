package days

class Day4 : Day(4) {

    private val assignmentPairs = inputList.map { it.split(",") }
        .map { toRange(it[0]).toList() to toRange(it[1]).toList() }

    override fun partOne(): Any {
        return assignmentPairs.count { it.first.containsAll(it.second) || it.second.containsAll(it.first) }
    }

    override fun partTwo(): Any {
        return assignmentPairs.count { it.first.any(it.second::contains) || it.second.any(it.first::contains) }
    }

    private fun toRange(strRange: String): IntRange {
        val (from, to) = strRange.split("-")
        return from.toInt().rangeTo(to.toInt())
    }
}
