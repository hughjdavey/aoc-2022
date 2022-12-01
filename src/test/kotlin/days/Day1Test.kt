package days

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.jupiter.api.Test

class Day1Test {

    private val dayOne = Day1()

    @Test
    fun testPartOne() {
        assertThat(dayOne.partOne(), `is`(24000))
    }

    @Test
    fun testPartTwo() {
        assertThat(dayOne.partTwo(), `is`(45000))
    }
}
