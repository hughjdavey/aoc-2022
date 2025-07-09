package days

import xyz.hughjd.aocutils.Coords.Coord

class Day15 : Day(15) {

    override fun partOne(): Any {
        val sensors = inputList
            .map { Regex("-?\\d+").findAll(it).map { it.groupValues }.toList().flatten() }
            .map { it.map(String::toInt) }
            .map { Sensor(Coord(it[0], it[1]), Coord(it[2], it[3])) }


        sensors.forEach(System.err::println)
        return beaconCannotBe(sensors, 10)
    }

    override fun partTwo(): Any {
        return 15
    }

    data class Sensor(val position: Coord, val closestBeacon: Coord, val distance: Int = position.manhattan(closestBeacon))

    fun beaconCannotBe(sensors: List<Sensor>, y: Int): Int {
        return sensors.fold(0 to mutableSetOf<Int>()) { (count, seenXs), sensor ->
            var c = count
            if (sensor.position.y == y) {
                seenXs.add(sensor.position.x)
            }
            if (sensor.closestBeacon.y == y) {
                seenXs.add(sensor.closestBeacon.x)
            }

            for (x in (sensor.position.x - sensor.distance..sensor.position.x + sensor.distance)) {
                if (!seenXs.contains(x) && sensor.position.manhattan(Coord(x, y)) <= sensor.distance) {
                    seenXs.add(x)
                    c++
                }
            }

            c to seenXs
        }.first
    }
}
