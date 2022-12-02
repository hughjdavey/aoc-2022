package util

import java.io.File

object DayCreator {

    private const val DAY_TEMPLATE = """
        package days

        class DayX : Day(X) {
        
            override fun partOne(): Any {
                return X
            }
        
            override fun partTwo(): Any {
                return X
            }
        }
    """

    private const val DAY_TEST_TEMPLATE = """
        package days

        import org.hamcrest.MatcherAssert.assertThat
        import org.hamcrest.Matchers.`is`
        import org.junit.jupiter.api.Test
        
        class DayXTest {
        
            private val dayX = DayX()
        
            @Test
            fun testPartOne() {
                assertThat(dayX.partOne(), `is`(X))
            }
        
            @Test
            fun testPartTwo() {
                assertThat(dayX.partTwo(), `is`(X))
            }
        }
    """

    @JvmStatic
    fun main(args: Array<String>) {
        if (args.isEmpty()) {
            printError("Please specify the day number you wish to create")
            return
        }

        val day = args.first().toIntOrNull()
        if (day == null || day < 1 || day > 25) {
            printError("Day must be a number from 1-25")
            return
        }

        val filesToCreate = getFilesToCreate(day)
        val alreadyExist = filesToCreate.filter { it.exists() }
        if (alreadyExist.isNotEmpty()) {
            printError("The following files already exist:\n%s\nAborting so as not to overwrite them"
                .format(alreadyExist.joinToString("\n") { "  - ${it.absolutePath}" }))
            return
        }

        println("Creating needed files for Day $day")
        filesToCreate.forEach {
            val template = if (it.absolutePath.endsWith("$day.kt")) DAY_TEMPLATE else if (it.absolutePath.endsWith("Test.kt")) DAY_TEST_TEMPLATE else ""
            val text = if (template.isEmpty()) "" else template.trimIndent().replace("X", day.toString())
            it.writeText("$text\n")
            println("  - ${it.absolutePath}")
        }
    }

    private fun getFilesToCreate(day: Int): List<File> {
        return listOf(
            "src/main/kotlin/days/Day$day.kt",
            "src/main/resources/input_day_$day.txt",
            "src/test/kotlin/days/Day${day}Test.kt",
            "src/test/resources/input_day_$day.txt",
        ).map { File(it) }
    }

    private fun printError(error: String) {
        System.err.println("Error calling startDay: $error")
        System.err.println("\tExample usage: `gradle startDay --args 12`")
    }
}
