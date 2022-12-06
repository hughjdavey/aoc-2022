package days

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Test

class Day6Test {

    private val day6 = Day6()

    @Test
    fun testPartOne() {
        assertThat(day6.partOne(), `is`(7))
    }

    @Test
    fun testPartOneAdditionalExamples() {
        assertThat(day6.firstStartOfPacket("bvwbjplbgvbhsrlpgdmjqwftvncz"), `is`(5))
        assertThat(day6.firstStartOfPacket("nppdvjthqldpwncqszvftbrmjlhg"), `is`(6))
        assertThat(day6.firstStartOfPacket("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg"), `is`(10))
        assertThat(day6.firstStartOfPacket("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"), `is`(11))
    }

    @Test
    fun testPartTwo() {
        assertThat(day6.partTwo(), `is`(19))
    }

    @Test
    fun testPartTwoAdditionalExamples() {
        assertThat(day6.firstStartOfMessage("bvwbjplbgvbhsrlpgdmjqwftvncz"), `is`(23))
        assertThat(day6.firstStartOfMessage("nppdvjthqldpwncqszvftbrmjlhg"), `is`(23))
        assertThat(day6.firstStartOfMessage("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg"), `is`(29))
        assertThat(day6.firstStartOfMessage("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"), `is`(26))
    }

    @Test
    fun testCharsAllDifferent() {
        assertThat("abcde".charsAllDifferent(), `is`(true))
        assertThat("abcdb".charsAllDifferent(), `is`(false))
    }
}
