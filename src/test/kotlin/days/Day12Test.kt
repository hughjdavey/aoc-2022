package days

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Test

class Day12Test {

    private val day12 = Day12()

    @Test
    fun testPartOne() {
        assertThat(day12.partOne(), `is`(31))
    }

    @Test
    fun testPartTwo() {
        assertThat(day12.partTwo(), `is`(29))
    }
}
