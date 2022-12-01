package days

class Day1 : Day(1) {

    private val totals = inputString.split(Regex("^$", RegexOption.MULTILINE))
        .map { it.lines().filterNot { it.isNullOrBlank() } }
        .map { it.sumOf { it.toInt() } }

    override fun partOne(): Any {
        return totals.max()
    }

    override fun partTwo(): Any {
        return totals.sortedDescending().take(3).sum()
    }
}
