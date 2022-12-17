package days

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Test

class Day14Test {

    private val day14 = Day14()

    @Test
    fun testPartOne() {
        assertThat(day14.partOne(), `is`(24))
    }

    @Test
    fun testPartTwo() {
        assertThat(day14.partTwo(), `is`(93))
    }
}
