package days

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.jupiter.api.Test

class Day2Test {

    private val day2 = Day2()

    @Test
    fun testPartOne() {
        assertThat(day2.partOne(), `is`(15))
    }

    @Test
    fun testPartTwo() {
        assertThat(day2.partTwo(), `is`(12))
    }
}
