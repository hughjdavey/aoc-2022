package days

class Day1 : Day(1) {

    private val totals = inputList.fold(listOf(0)) { acc, elem ->
        if (elem.isBlank()) acc + 0 else acc.dropLast(1) + (acc.last() + elem.toInt())
    }

    override fun partOne(): Any {
        return totals.max()
    }

    override fun partTwo(): Any {
        return totals.sortedDescending().take(3).sum()
    }
}
