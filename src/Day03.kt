fun main() {

    fun Char.priority(): Int {
        return this.code - if (this.isLowerCase()) 96 else 38
    }

    fun part1(input: List<String>): Int {
        var prioSum = 0
        input.forEach { line ->
            val chunked = line.chunked(line.length / 2)
            val duplicate = chunked[0].toSet().intersect(chunked[1].toSet()).first()
            prioSum += duplicate.priority()
        }
        return prioSum
    }

    fun part2(input: List<String>): Int {
        return input.asSequence()
            .map { it.toSet() }
            .chunked(3)
            .map { it.reduce { intersection, prev -> intersection.intersect(prev) } }
            .map { it.first().priority() }
            .sum()
    }

    val input = readInput("Day03")


    println(part1(input))
    println(part2(input))
}


