package days

class Day3 : Day(3) {

    private val priorities = ('a'..'z').plus('A'..'Z').zip(1..52).toMap()

    override fun partOne(): Any {
        return inputList.map { sack -> sack.chunked(sack.length / 2) }
            .map { compartments -> compartments[0].first { compartments[1].contains(it) } }
            .sumOf { item -> priorities.getOrDefault(item, 0) }
    }

    override fun partTwo(): Any {
        return inputList.windowed(3, 3)
            .map { sack -> sack[0].first { sack[1].contains(it) && sack[2].contains(it) } }
            .sumOf { item -> priorities.getOrDefault(item, 0) }
    }
}
