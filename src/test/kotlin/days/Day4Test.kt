package days

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Test

class Day4Test {

    private val day4 = Day4()

    @Test
    fun testPartOne() {
        assertThat(day4.partOne(), `is`(2))
    }

    @Test
    fun testPartTwo() {
        assertThat(day4.partTwo(), `is`(4))
    }
}
