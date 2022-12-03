package days

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Test

class Day3Test {

    private val day3 = Day3()

    @Test
    fun testPartOne() {
        assertThat(day3.partOne(), `is`(157))
    }

    @Test
    fun testPartTwo() {
        assertThat(day3.partTwo(), `is`(70))
    }
}
