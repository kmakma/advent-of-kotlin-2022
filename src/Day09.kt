fun main() {
    val oneX = Coord2D(1, 0)
    val oneY = Coord2D(0, 1)

    data class Motion(val dir: Char, val count: Int)

    fun mapToMotions(input: List<String>): List<Motion> {
        return input.map {
            val (d, c) = it.split(" ")
            Motion(d.first(), c.toInt())
        }
    }

    fun moveInDir(coor: Coord2D, dir: Char): Coord2D {
        return when (dir) {
            'L' -> Coord2D(coor.x - 1, coor.y)
            'R' -> Coord2D(coor.x + 1, coor.y)
            'U' -> Coord2D(coor.x, coor.y + 1)
            'D' -> Coord2D(coor.x, coor.y - 1)
            else -> error("bad direction $dir")
        }
    }

    fun follow(head: Coord2D, tail: Coord2D): Coord2D {
        var newTail = tail
        while (head != newTail && !newTail.adjacentTo(head)) {
            when {
                head.x < newTail.x -> newTail -= oneX
                head.x > newTail.x -> newTail += oneX
            }
            when {
                head.y < newTail.y -> newTail -= oneY
                head.y > newTail.y -> newTail += oneY
            }
        }
        return newTail
    }

    fun part1(input: List<Motion>): Int {
        val visited = mutableSetOf<Coord2D>()
        var head = Coord2D(0, 0)
        var tail = Coord2D(0, 0)
        visited.add(tail)
        input.forEach { m ->
            repeat(m.count) {
                head = moveInDir(head, m.dir)
                tail = follow(head, tail)
                visited.add(tail)
            }
        }
        return visited.size
    }

    fun part2(input: List<Motion>): Int {
        val visited = mutableSetOf<Coord2D>()
        val rope = MutableList(10) { Coord2D(0, 0) }
        visited.add(rope.last())
        input.forEach { m ->
            repeat(m.count) {
                rope[0] = moveInDir(rope[0], m.dir)
                for (i in 1..rope.lastIndex) {
                    rope[i] = follow(rope[i - 1], rope[i])
                }
                visited.add(rope.last())
            }
        }
        return visited.size
    }

    val testInput1 = mapToMotions(readInput("Day09test1"))
    check(part1(testInput1) == 13)
    check(part2(testInput1) == 1)
    val testInput2 = mapToMotions(readInput("Day09test2"))
    check(part2(testInput2) == 36)

    val input = mapToMotions(readInput("Day09"))

    println(part1(input))
    println(part2(input))
}


