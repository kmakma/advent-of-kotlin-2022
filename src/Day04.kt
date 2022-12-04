fun main() {

    fun part1(input: List<List<Int>>): Int {
        return input.count { list ->
            (list[0] <= list[2] && list[1] >= list[3])
                    || (list[0] >= list[2] && list[1] <= list[3])
        }
    }

    fun part2(input: List<List<Int>>): Int {
        return input.count { list ->
            (list[0] <= list[2] && list[2] <= list[1])
                    || (list[2] <= list[0] && list[0] <= list[3])
        }
    }

    val input = readInput("Day04")
        .map { line ->
            line.split(',', '-')
                .map { it.toInt() }
        }

    println(part1(input))
    println(part2(input))
}


