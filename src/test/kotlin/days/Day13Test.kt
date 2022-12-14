package days

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Test

class Day13Test {

    private val day13 = Day13()

    @Test
    fun testPartOne() {
        assertThat(day13.partOne(), `is`(13))
    }

    @Test
    fun testPartTwo() {
        assertThat(day13.partTwo(), `is`(140))
    }
}
