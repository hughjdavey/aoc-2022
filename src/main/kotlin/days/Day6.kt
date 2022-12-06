package days

class Day6 : Day(6) {

    override fun partOne(): Any {
        return firstStartOfPacket(inputString)
    }

    override fun partTwo(): Any {
        return firstStartOfMessage(inputString)
    }

    fun firstStartOfPacket(buffer: String): Int {
        return endOfFirstMarker(buffer, 4)
    }

    fun firstStartOfMessage(buffer: String): Int {
        return endOfFirstMarker(buffer, 14)
    }

    private fun endOfFirstMarker(buffer: String, markerLength: Int): Int {
        return buffer.windowedSequence(markerLength)
            .find(String::charsAllDifferent)
            ?.let { buffer.indexOf(it) + markerLength } ?: 0
    }

}

fun String.charsAllDifferent(): Boolean {
    return this.toCharArray().none { c -> this.count { c2 -> c == c2 } > 1 }
}
