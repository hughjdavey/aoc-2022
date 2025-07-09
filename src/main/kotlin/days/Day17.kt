package days

class Day17 : Day(17) {

    private val rocks = CircularList(listOf(
        "####",

        """
        .#.
        ###
        .#.    
        """.trimIndent(),

        """
        ..#
        ..#
        ###    
        """.trimIndent(),

        """
        #
        #
        #    
        #    
        """.trimIndent(),

        """
        ##
        ##    
        """.trimIndent()
    ))

    val jets = CircularList(inputString.toCharArray().asList())

    override fun partOne(): Any {
        rocks.forEach { rock ->
            System.err.println(rock)
            System.err.println()
        }
        return 17
    }

    override fun partTwo(): Any {
        return 17
    }

    fun fall(until: Int) {
        generateSequence(0 to "")
    }

    class CircularList<T>(private val ts: List<T>) : List<T> by ts {

        private var head = 0

        fun next(): T {
            if (head > ts.lastIndex) {
                head = 0
            }
            return ts[head++]
        }
    }
}
