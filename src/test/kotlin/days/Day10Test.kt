package days

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Test

class Day10Test {

    private val day10 = Day10()

    @Test
    fun testPartOne() {
        assertThat(day10.partOne(), `is`(13140))
    }

    @Test
    fun testPartOneSmallProgram() {
        val program = listOf(Day10.Noop(), Day10.AddX(3), Day10.AddX(-5))
        val cpu = Day10.CPU(true)
        cpu.run(program)
        assertThat(cpu.getX(), `is`(-1))
    }

    @Test
    fun testPartTwo() {
        assertThat(day10.partTwo(), `is`("""
            
            ##..##..##..##..##..##..##..##..##..##..
            ###...###...###...###...###...###...###.
            ####....####....####....####....####....
            #####.....#####.....#####.....#####.....
            ######......######......######......####
            #######.......#######.......#######.....
        """.trimIndent()))
    }
}
