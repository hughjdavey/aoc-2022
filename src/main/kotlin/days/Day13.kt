package days

import days.Day13.Order.RIGHT
import days.Day13.Order.SAME
import days.Day13.Order.WRONG
import xyz.hughjd.aocutils.Collections.takeWhileInclusive
import java.util.Stack

class Day13 : Day(13) {

    override fun partOne(): Any {
        return inputList.asSequence().filterNot { it.isEmpty() }.map { parsePackets(it) }
            .chunked(2).map { compare(it[0], it[1]) }
            .mapIndexedNotNull { index, order -> if (order == RIGHT) index + 1 else null }
            .sum()
    }

    override fun partTwo(): Any {
        val decoderPackets = listOf( listOf(listOf(listOf(2))), listOf(listOf(listOf(6))) )
        return inputList.asSequence().filterNot { it.isEmpty() }.map { parsePackets(it) }
            .plus(decoderPackets).sortedWith { a1, a2 -> compare(a1, a2).ordinal - 1 }
            .mapIndexedNotNull { index, packet -> if (packet in decoderPackets) index + 1 else null }
            .fold(1) { acc, elem -> acc * elem }
    }

    enum class Order { RIGHT, SAME, WRONG }

    fun compare(packets1: List<Any>, packets2: List<Any>): Order {
        val foo = generateSequence(0) { it + 1 }.map { packets1.getOrNull(it) to packets2.getOrNull(it) }.map {
            val (left, right) = it
            when {
                left == null && right == null -> SAME
                left == null && right != null -> RIGHT
                left != null && right == null -> WRONG
                left is Int && right is Int -> if (left < right) RIGHT else if (left == right) SAME else WRONG
                left is List<*> && right is List<*> -> compare(left as List<Any>, right as List<Any>)
                else -> {           // exactly one value is an integer
                    val l = if (left is Int) listOf(left) else left
                    val r = if (right is Int) listOf(right) else right
                    compare(l as List<Int>, r as List<Int>)
                }
            }
        }.takeWhileInclusive { it == SAME }.take(10).toList()
        return foo.last()
    }

    private fun parsePackets(input: String): List<Any> {
        val packets = mutableListOf<Any>()
        val stack = Stack<MutableList<Any>>()
        var currentInt = ""

        fun getCurrentList(): MutableList<Any> {
            return if (!stack.empty()) stack.peek() else packets
        }

        fun addCurrentInt() {
            if (currentInt.isNotEmpty()) {
                getCurrentList().add(currentInt.toInt())
                currentInt = ""
            }
        }

        input.drop(1).dropLast(1).forEach { c ->
            when (c) {
                '[' -> {
                    val list = mutableListOf<Any>()
                    getCurrentList().add(list)
                    stack.push(list)
                }
                ']' -> {
                    addCurrentInt()
                    stack.pop()
                }
                ',' -> addCurrentInt()
                else -> currentInt += c
            }
        }
        addCurrentInt()
        return packets
    }
}
