fun main() {

    fun neighborsOf(coord: Coord2D, map: Map<Coord2D, Int>): List<Coord2D> {
        val neighbors = mutableListOf<Coord2D>()
        if (coord.x > 0) {
            val newCoord = coord.moved(-1, 0)
            if (map[newCoord]!! - map[coord]!! <= 1) neighbors.add(newCoord)
        }
        if (coord.y > 0) {
            val newCoord = coord.moved(0, -1)
            if (map[newCoord]!! - map[coord]!! <= 1) neighbors.add(newCoord)
        }
        val newCoordX = coord.moved(1, 0)
        if (map.containsKey(newCoordX) && map[newCoordX]!! - map[coord]!! <= 1) neighbors.add(newCoordX)
        val newCoordY = coord.moved(0, 1)
        if (map.containsKey(newCoordY) && map[newCoordY]!! - map[coord]!! <= 1) neighbors.add(newCoordY)

        return neighbors
    }

    fun printDistanceMap(distances: Map<Coord2D, Int>) {
        val minX = distances.keys.minOf { it.x }
        val maxX = distances.keys.maxOf { it.x }
        val minY = distances.keys.minOf { it.y }
        val maxY = distances.keys.maxOf { it.y }
        for (x in minX..maxX) {
            for (y in minY..maxY) {
                val dStr = distances.getOrDefault(Coord2D(x, y), -1).toString().padStart(2, ' ')
                print("$dStr | ")
            }
            println()
        }
    }

    fun shortestPath(heights: Map<Coord2D, Int>, start: Coord2D, end: Coord2D): Int {
        val distances = mutableMapOf(start to 0)
        val queue = mutableListOf(start)
        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()
            val distance = distances[current]!!
            if (current == end) {
//                printDistanceMap(distances)
                return distance
            }
            val newEntries = neighborsOf(current, heights).filter { !distances.containsKey(it) }
            queue.addAll(newEntries)
            distances.putAll(newEntries.map { it to distance + 1 })
        }
        return -1
    }

    fun buildHeightMap(input: List<String>): Triple<Coord2D, Coord2D, Map<Coord2D, Int>> {
        lateinit var start: Coord2D
        lateinit var end: Coord2D
        val heights = input.flatMapIndexed { x, line ->
            line.mapIndexed { y, c ->
                Coord2D(x, y) to when (c) {
                    'E' -> {
                        end = Coord2D(x, y)
                        'z'.code - 97
                    }

                    'S' -> {
                        start = Coord2D(x, y)
                        'a'.code - 97
                    }

                    else -> c.code - 97
                }
            }
        }.toMap()
        return Triple(start, end, heights)
    }

    fun part1(input: List<String>): Int {
        val (start, end, heights) = buildHeightMap(input)
        return shortestPath(heights, start, end)
    }

    fun part2(input: List<String>): Int {
        val (_, end, heights) = buildHeightMap(input)
        return heights.filterValues { it == 0 }.keys.map { shortestPath(heights, it, end) }.filter { it > 0 }.min()
    }

//    val testinput = readInput("Day12_test")
//    println(part1(testinput))

    val input = readInput("Day12")

    println(part1(input))
    println(part2(input))
}



