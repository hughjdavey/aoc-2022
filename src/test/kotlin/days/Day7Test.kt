package days

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Test

class Day7Test {

    private val day7 = Day7()

    @Test
    fun testPartOne() {
        assertThat(day7.partOne(), `is`(95437))
    }

    @Test
    fun testPartTwo() {
        assertThat(day7.partTwo(), `is`(24933642))
    }
}
