package days

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.hasSize
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.notNullValue
import org.junit.jupiter.api.Test

class Day10Test {

    private val day10 = Day10()

    @Test
    fun testPartOne() {
        assertThat(day10.partOne(), `is`(13140))
    }

    @Test
    fun testPartOneSmallProgram() {
        val program = listOf(Day10.Noop(), Day10.AddX(v = 3), Day10.AddX(v = -5))
        val run = Day10.CPU().run(program)
        assertThat(run, hasSize(6))
        assertThat(run.last(), `is`(6 to -1))
    }

    @Test
    fun testSignalStrength() {
        val run = Day10.CPU().run(day10.program)
        assertSignalStrength(run, 20, 420)
        assertSignalStrength(run, 60, 1140)
        assertSignalStrength(run, 100, 1800)
        assertSignalStrength(run, 140, 2940)
        assertSignalStrength(run, 180, 2880)
        assertSignalStrength(run, 220, 3960)
    }
    
    private fun assertSignalStrength(run: List<Pair<Int, Int>>, cycle: Int, strength: Int) {
        val cycleToXPair = run.find { it.first == cycle }
        assertThat(cycleToXPair, notNullValue())
        assertThat(cycleToXPair!!.first * cycleToXPair.second, `is`(strength))
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
