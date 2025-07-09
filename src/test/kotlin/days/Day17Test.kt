package days

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.hasSize
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Test

class Day17Test {

    private val day17 = Day17()

    @Test
    fun testPartOne() {
        assertThat(day17.partOne(), `is`(3068))
    }

    @Test
    fun testPartTwo() {
        assertThat(day17.partTwo(), `is`(17))
    }

    @Test
    fun testCircularList() {
        val list = Day17.CircularList(listOf(1, 2, 3, 4))
        assertThat(list, hasSize(4))
        assertThat(list.next(), `is`(1))
        assertThat(list.next(), `is`(2))
        assertThat(list.next(), `is`(3))
        assertThat(list.next(), `is`(4))
        assertThat(list.next(), `is`(1))
        assertThat(list.next(), `is`(2))
        assertThat(list.next(), `is`(3))
        assertThat(list.next(), `is`(4))
    }
}
