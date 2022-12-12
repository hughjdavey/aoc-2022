package days

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.contains
import org.hamcrest.Matchers.empty
import org.hamcrest.Matchers.hasSize
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Test
import java.math.BigInteger

class Day11Test {

    private val day11 = Day11()

    @Test
    fun testPartOne() {
        assertThat(day11.partOne(), `is`(10605))
    }

    @Test
    fun testPartTwo() {
        assertThat(day11.partTwo(), `is`(2713310158))
    }

    @Test
    fun testGetMonkeys() {
        val monkeys = day11.getMonkeys()
        assertThat(monkeys, hasSize(4))

        assertThat(monkeys[0].index, `is`(0))
        assertThat(monkeys[0].items, contains(79, 98))
        assertThat(monkeys[0].operation, `is`("*" to "19"))
        assertThat(monkeys[0].operation(4), `is`(76))
        assertThat(monkeys[0].divisor, `is`(23))
        assertThat(monkeys[0].onTrue, `is`(2))
        assertThat(monkeys[0].onFalse, `is`(3))

        assertThat(monkeys[1].index, `is`(1))
        assertThat(monkeys[1].items, contains(54, 65, 75, 74))
        assertThat(monkeys[1].operation, `is`("+" to "6"))
        assertThat(monkeys[1].operation(4), `is`(10))
        assertThat(monkeys[1].divisor, `is`(19))
        assertThat(monkeys[1].onTrue, `is`(2))
        assertThat(monkeys[1].onFalse, `is`(0))

        assertThat(monkeys[2].index, `is`(2))
        assertThat(monkeys[2].items, contains(79, 60, 97))
        assertThat(monkeys[2].operation, `is`("*" to "old"))
        assertThat(monkeys[2].operation(4), `is`(16))
        assertThat(monkeys[2].divisor, `is`(13))
        assertThat(monkeys[2].onTrue, `is`(1))
        assertThat(monkeys[2].onFalse, `is`(3))

        assertThat(monkeys[3].index, `is`(3))
        assertThat(monkeys[3].items, contains(74))
        assertThat(monkeys[3].operation, `is`("+" to "3"))
        assertThat(monkeys[3].operation(4), `is`(7))
        assertThat(monkeys[3].divisor, `is`(17))
        assertThat(monkeys[3].onTrue, `is`(0))
        assertThat(monkeys[3].onFalse, `is`(1))
    }

    @Test
    fun testRound() {
        val monkeys = day11.getMonkeys()
        val afterRound1 = day11.round(monkeys)
        assertThat(afterRound1[0].items, contains(20, 23, 27, 26))
        assertThat(afterRound1[1].items, contains(2080, 25, 167, 207, 401, 1046))
        assertThat(afterRound1[2].items, empty())
        assertThat(afterRound1[3].items, empty())
    }

    @Test
    fun testPlay() {
        val afterRound10 = day11.play(day11.getMonkeys(), 10)
        assertThat(afterRound10[0].items, contains(91, 16, 20, 98))
        assertThat(afterRound10[1].items, contains(481, 245, 22, 26, 1092, 30))
        assertThat(afterRound10[2].items, empty())
        assertThat(afterRound10[3].items, empty())

        val afterRound15 = day11.play(day11.getMonkeys(), 15)
        assertThat(afterRound15[0].items, contains(83, 44, 8, 184, 9, 20, 26, 102))
        assertThat(afterRound15[1].items, contains(110, 36))
        assertThat(afterRound15[2].items, empty())
        assertThat(afterRound15[3].items, empty())

        val afterRound20 = day11.play(day11.getMonkeys(), 20)
        assertThat(afterRound20[0].items, contains(10, 12, 14, 26, 34))
        assertThat(afterRound20[1].items, contains(245, 93, 53, 199, 115))
        assertThat(afterRound20[2].items, empty())
        assertThat(afterRound20[3].items, empty())
        assertThat(afterRound20[0].inspections, `is`(101))
        assertThat(afterRound20[1].inspections, `is`(95))
        assertThat(afterRound20[2].inspections, `is`(7))
        assertThat(afterRound20[3].inspections, `is`(105))
    }

    @Test
    fun test10k() {
        assertThat(day11.play(day11.getMonkeys(), 1, false).map { it.inspections }, contains(2, 4, 3, 6))
        assertThat(day11.play(day11.getMonkeys(), 20, false).map { it.inspections }, contains(99, 97, 8, 103))
        assertThat(day11.play(day11.getMonkeys(), 1000, false).map { it.inspections }, contains(5204, 4792, 199, 5192))
        assertThat(day11.play(day11.getMonkeys(), 5000, false).map { it.inspections }, contains(26075, 23921, 974, 26000))
        assertThat(day11.play(day11.getMonkeys(), 10000, false).map { it.inspections }, contains(52166, 47830, 1938, 52013))
    }
}
