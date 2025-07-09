package util

import days.Day
import org.reflections.Reflections
import kotlin.math.max
import kotlin.math.roundToInt
import kotlin.time.ExperimentalTime
import kotlin.time.TimedValue
import kotlin.time.measureTimedValue

@ExperimentalTime
object Runner {

    private val reflections = Reflections("days")

    @JvmStatic
    fun main(args: Array<String>) {
        if (args.isNotEmpty()) {
            val day = try {
                args[0].toInt()
            }
            catch (e: NumberFormatException) {
                printError("Day argument must be an integer")
                return
            }

            val dayClass = getAllDayClasses()?.find { dayNumber(it.simpleName) == day }
            if (dayClass != null) {
                printDay(dayClass)
            }
            else {
                printError("Day $day not found")
            }
        }
        else {
            val allDayClasses = getAllDayClasses()
            if (allDayClasses != null) {
                allDayClasses.sortedBy { dayNumber(it.simpleName) }.forEach { printDay(it) }
            }
            else {
                printError("Couldn't find day classes - make sure you're in the right directory and try building again")
            }
        }
    }

    private fun getAllDayClasses(): MutableSet<Class<out Day>>? {
        return reflections.getSubTypesOf(Day::class.java)
    }

    private fun printDay(dayClass: Class<out Day>) {
        println("\n=== DAY ${dayNumber(dayClass.simpleName)} ===")
        val day = dayClass.constructors[0].newInstance() as Day

        val partOne = measureTimedValue { day.partOne() }
        val partTwo = measureTimedValue { day.partTwo() }
        printParts(partOne, partTwo)
    }

    private fun printParts(partOne: TimedValue<Any>, partTwo: TimedValue<Any>) {
        val p1Value = partOne.value.toString()
        val p2Value = partTwo.value.toString()

        val padding = max(partOne.value.toString().length, partTwo.value.toString().length) + 14        // 14 is 8 (length of 'Part 1: ') + 6 more

        var (p1Padding, p2Padding) = padding to padding
        if (p1Value.contains('\n') && !p2Value.contains('\n')) {
            p2Padding = lastLineLength(p2Value) + 6
        }
        if (!p1Value.contains('\n') && p2Value.contains('\n')) {
            p1Padding = lastLineLength(p2Value) + 6
        }

        println("Part 1: ${partOne.value}".padEnd(p1Padding, ' ') + "(${partOne.duration})")
        println("Part 2: ${partTwo.value}".padEnd(p2Padding, ' ') + "(${partTwo.duration})")
    }

    private fun lastLineLength(str: String): Int {
        return str.split('\n').last().length
    }

    private fun printError(message: String) {
        System.err.println("\n=== ERROR ===\n$message")
    }

    private fun dayNumber(dayClassName: String) = dayClassName.replace("Day", "").toInt()
}
