package days

import days.Day5Test.StackMatcher.Companion.popsElements
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.contains
import org.hamcrest.Matchers.empty
import org.hamcrest.Matchers.hasSize
import org.hamcrest.Matchers.`is`
import org.hamcrest.TypeSafeMatcher
import org.junit.jupiter.api.Test
import java.util.Stack

class Day5Test {

    private val day5 = Day5()

    private val stacksInput = listOf(
        "    [D]",
        "[N] [C]",
        "[Z] [M] [P]",
        " 1   2   3",
    )

    private val movesInput = listOf(
        "move 1 from 2 to 1",
        "move 3 from 1 to 3",
        "move 2 from 2 to 1",
        "move 1 from 1 to 2",
        "move 12 from 345 to 6789",
    )

    @Test
    fun testPartOne() {
        assertThat(day5.partOne(), `is`("CMZ"))
    }

    @Test
    fun testPartTwo() {
        assertThat(day5.partTwo(), `is`("MCD"))
    }

    @Test
    fun testGetStacks() {
        val stacks = Day5.getStacks(stacksInput)
        assertThat(stacks, hasSize(3))
        assertThat(stacks[0], popsElements('N', 'Z'))
        assertThat(stacks[1], popsElements('D', 'C', 'M'))
        assertThat(stacks[2], popsElements('P'))
    }

    @Test
    fun testGetMoves() {
        val moves = Day5.getMoves(movesInput)
        assertThat(moves, hasSize(5))
        assertThat(moves, contains(
            Day5.Move(1, 2, 1),
            Day5.Move(3, 1, 3),
            Day5.Move(2, 2, 1),
            Day5.Move(1, 1, 2),
            Day5.Move(12, 345, 6789),
        ))
    }

    @Test
    fun testMove9000() {
        val stacks = Day5.getStacks(stacksInput)
        val moves = Day5.getMoves(movesInput)
        assertThat(stacks[0].toList().reversed(), contains('N', 'Z'))
        assertThat(stacks[1].toList().reversed(), contains('D', 'C', 'M'))
        assertThat(stacks[2].toList().reversed(), contains('P'))

        day5.move9000(moves[0], stacks)
        assertThat(stacks[0].toList().reversed(), contains('D', 'N', 'Z'))
        assertThat(stacks[1].toList().reversed(), contains('C', 'M'))
        assertThat(stacks[2].toList().reversed(), contains('P'))

        day5.move9000(moves[1], stacks)
        assertThat(stacks[0].toList().reversed(), empty())
        assertThat(stacks[1].toList().reversed(), contains('C', 'M'))
        assertThat(stacks[2].toList().reversed(), contains('Z', 'N', 'D', 'P'))

        day5.move9000(moves[2], stacks)
        assertThat(stacks[0].toList().reversed(), contains('M', 'C'))
        assertThat(stacks[1].toList().reversed(), empty())
        assertThat(stacks[2].toList().reversed(), contains('Z', 'N', 'D', 'P'))

        day5.move9000(moves[3], stacks)
        assertThat(stacks[0].toList().reversed(), contains('C'))
        assertThat(stacks[1].toList().reversed(), contains('M'))
        assertThat(stacks[2].toList().reversed(), contains('Z', 'N', 'D', 'P'))
    }

    @Test
    fun testMove9001() {
        val stacks = Day5.getStacks(stacksInput)
        val moves = Day5.getMoves(movesInput)
        assertThat(stacks[0].toList().reversed(), contains('N', 'Z'))
        assertThat(stacks[1].toList().reversed(), contains('D', 'C', 'M'))
        assertThat(stacks[2].toList().reversed(), contains('P'))

        day5.move9001(moves[0], stacks)
        System.err.println(stacks)
        assertThat(stacks[0].toList().reversed(), contains('D', 'N', 'Z'))
        assertThat(stacks[1].toList().reversed(), contains('C', 'M'))
        assertThat(stacks[2].toList().reversed(), contains('P'))

        day5.move9001(moves[1], stacks)
        assertThat(stacks[0].toList().reversed(), empty())
        assertThat(stacks[1].toList().reversed(), contains('C', 'M'))
        assertThat(stacks[2].toList().reversed(), contains('D', 'N', 'Z', 'P'))

        day5.move9001(moves[2], stacks)
        assertThat(stacks[0].toList().reversed(), contains('C', 'M'))
        assertThat(stacks[1].toList().reversed(), empty())
        assertThat(stacks[2].toList().reversed(), contains('D', 'N', 'Z', 'P'))

        day5.move9001(moves[3], stacks)
        assertThat(stacks[0].toList().reversed(), contains('M'))
        assertThat(stacks[1].toList().reversed(), contains('C'))
        assertThat(stacks[2].toList().reversed(), contains('D', 'N', 'Z', 'P'))
    }

    private class StackMatcher<T>(vararg val expectedChars: Char) : TypeSafeMatcher<Stack<T>>() {

        override fun describeTo(description: Description?) {
            description?.appendText("stack containing " + expectedChars.toList())
        }

        override fun matchesSafely(stack: Stack<T>?): Boolean {
            if (stack == null) {
                return false
            }
            for (i in 0 until stack.size) {
                if (stack.pop() != expectedChars[i]) {
                    return false
                }
            }
            return true
        }

        companion object {

            fun <T> popsElements(vararg expectedChars: Char): Matcher<Stack<T>> {
                return StackMatcher(*expectedChars)
            }
        }
    }
}
