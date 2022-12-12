package days

import xyz.hughjd.aocutils.Collections.split

class Day11 : Day(11) {

    override fun partOne(): Any {
        return play(getMonkeys(), 20).map { it.inspections }
            .sortedDescending().take(2)
            .let { it[0] * it[1] }
    }

    override fun partTwo(): Any {
        return play(getMonkeys(), 10000, false).map { it.inspections }
            .sortedDescending().take(2)
            .let { it[0].toLong() * it[1] }
    }

    fun getMonkeys(): List<Monkey> {
        return inputList.split("").mapIndexed { index, it ->
            val (op, n) = Regex("old ([+|*]) (\\d+|old)").find(it[2].trim().replace("Operation: ", ""))!!.destructured
            val test = it[3].split(" ").last().toLong()
            val onTrue = it[4].split(" ").last().toInt()
            val onFalse = it[5].split(" ").last().toInt()
            Monkey(index, ArrayDeque(getItems(it[1])), op to n, test, onTrue, onFalse)
        }
    }

    fun play(monkeys: List<Monkey>, rounds: Int, reliefOn: Boolean = true): List<Monkey> {
        (0 until rounds).forEach { round(monkeys, reliefOn) }
        return monkeys
    }

    fun round(monkeys: List<Monkey>, reliefOn: Boolean = true): List<Monkey> {
        monkeys.forEach { monkey -> turn(monkey, monkeys, reliefOn) }
        return monkeys
    }

    private fun turn(monkey: Monkey, monkeys: List<Monkey>, reliefOn: Boolean) {
        for (i in monkey.items.indices) {
            var worryLevel = monkey.items.removeFirst()
            worryLevel = monkey.operation(worryLevel)
            if (reliefOn) {
                worryLevel /= 3
            }
            else {
                worryLevel %= monkeys.map { it.divisor }.fold(1L) { acc, elem -> acc * elem }
            }
            val nextMonkeyIndex = monkey.test(worryLevel)
            val nextMonkey = monkeys.find { it.index == nextMonkeyIndex }!!
            nextMonkey.items.addLast(worryLevel)
        }
    }

    private fun getItems(str: String): List<Long> {
        return Regex("\\d+").findAll(str.trim().replace("Starting items: ", ""))
            .map { it.value.toLong() }.toList()
    }

    data class Monkey(val index: Int, val items: ArrayDeque<Long>, val operation: Pair<String, String>, val divisor: Long, val onTrue: Int, val onFalse: Int) {

        var inspections = 0

        fun operation(old: Long): Long {
            inspections++
            val (op, n) = operation
            val num = if (n == "old") old else n.toLong()
            return if (op == "+") old + num else old * num
        }

        fun test(item: Long): Int {
            return if (item % divisor == 0L) onTrue else onFalse
        }
    }
}
