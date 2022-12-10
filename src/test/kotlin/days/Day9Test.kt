package days

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Test
import xyz.hughjd.aocutils.Coords.Coord

class Day9Test {

    private val day9 = Day9()

    @Test
    fun testPartOne() {
        assertThat(day9.partOne(), `is`(13))
    }

    @Test
    fun testPartTwo() {
        assertThat(day9.partTwo(), `is`(1))
    }

    @Test
    fun testPartTwoLargerExample() {
        val input = listOf(
            "R 5",
            "U 8",
            "L 8",
            "D 3",
            "R 17",
            "D 10",
            "L 25",
            "U 20",
        )
        assertThat(day9.partTwoTest(input).first, `is`(36))
    }

    @Test
    fun testTouching() {
        assertThat(Day9.Rope(Coord(0, 0), Coord(-1, 0)).touching(), `is`(true))
        assertThat(Day9.Rope(Coord(0, 0), Coord(1, 1)).touching(), `is`(true))
        assertThat(Day9.Rope(Coord(0, 0), Coord(0, 0)).touching(), `is`(true))
    }

    @Test
    fun testLongRope() {
        val (n, states) = day9.partTwoTest()
        assertThat(states[0], `is`("""
            ......
            ......
            ......
            ......
            0.....
        """.trimIndent()))

        assertThat(states[4], `is`("""
            ......
            ......
            ......
            ......
            43210.
        """.trimIndent()))

        assertThat(states[8], `is`("""
            ....0.
            ....1.
            ..432.
            .5....
            6.....
        """.trimIndent()))

        assertThat(states[11], `is`("""
            .01...
            ...2..
            ..43..
            .5....
            6.....
        """.trimIndent()))

        assertThat(states[12], `is`("""
            ..1...
            .0.2..
            ..43..
            .5....
            6.....
        """.trimIndent()))

        assertThat(states[16], `is`("""
            ......
            ...210
            ..43..
            .5....
            6.....
        """.trimIndent()))

        assertThat(states[17], `is`("""
            ......
            ...21.
            ..43.0
            .5....
            6.....
        """.trimIndent()))

        assertThat(states[22], `is`("""
            ......
            ......
            0123..
            .5....
            6.....
        """.trimIndent()))

        assertThat(states[24], `is`("""
            ......
            ......
            .103..
            .5....
            6.....
        """.trimIndent()))
    }
}
