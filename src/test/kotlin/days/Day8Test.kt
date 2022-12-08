package days

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.contains
import org.hamcrest.Matchers.containsInAnyOrder
import org.hamcrest.Matchers.hasSize
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Test
import xyz.hughjd.aocutils.Coords.Coord

class Day8Test {

    private val day8 = Day8()

    private val trees = Day8.Trees(listOf("30373", "25512", "65332", "33549", "35390"));

    @Test
    fun testPartOne() {
        assertThat(day8.partOne(), `is`(21))
    }

    @Test
    fun testPartTwo() {
        assertThat(day8.partTwo(), `is`(8))
    }

    @Test
    fun testGetTrees() {
        assertThat(trees.trees.flatten(), hasSize(25))
        assertThat(trees.trees.flatten(), contains(
            3, 0, 3, 7, 3,
            2, 5, 5, 1, 2,
            6, 5, 3, 3, 2,
            3, 3, 5, 4, 9,
            3, 5, 3, 9, 0,
        ))
    }

    @Test
    fun testIsVisible() {
        assertThat(trees.isVisible(Coord(1, 1)), `is`(true))
        assertThat(trees.isVisible(Coord(2, 1)), `is`(true))
        assertThat(trees.isVisible(Coord(3, 1)), `is`(false))
        assertThat(trees.isVisible(Coord(1, 2)), `is`(true))
        assertThat(trees.isVisible(Coord(2, 2)), `is`(false))
        assertThat(trees.isVisible(Coord(3, 2)), `is`(true))

        assertThat(trees.isVisible(Coord(1, 3)), `is`(false))
        assertThat(trees.isVisible(Coord(2, 3)), `is`(true))
        assertThat(trees.isVisible(Coord(3, 3)), `is`(false))
    }

    @Test
    fun testGetTreesToEdgeOfGrid() {
        val toEdge = trees.getTreesToEdgeOfGrid(Coord(2, 2))
        assertThat(toEdge.flatten(), containsInAnyOrder(6, 5, 3, 2, 3, 5, 5, 3))

        // check that the (in order) trees to right, left, down and up are correct
        assertThat(toEdge, contains(listOf(3, 2), listOf(5, 6), listOf(5, 3), listOf(5, 3)))
    }

    @Test
    fun testGetTreesToEdgeOfGridConsideringView() {
        assertThat(trees.getTreesToEdgeOfGrid(Coord(2, 1), true), contains(listOf(1, 2), listOf(5), listOf(3, 5), listOf(3)))
        assertThat(trees.getTreesToEdgeOfGrid(Coord(2, 3), true), contains(listOf(4, 9), listOf(3, 3), listOf(3), listOf(3, 5)))
    }

    @Test
    fun testScenicScore() {
        assertThat(trees.scenicScore(Coord(2, 1)), `is`(4))
        assertThat(trees.scenicScore(Coord(2, 3)), `is`(8))
    }
}
