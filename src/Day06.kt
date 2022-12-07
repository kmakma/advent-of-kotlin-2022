fun main() {
    fun indexOfDistinctSubstring(input: String, length: Int): Int {
        return input.windowedSequence(length)
            .indexOfFirst { window -> window.toSet().size == length } + length
    }

    fun part1(input: String): Int {
        return indexOfDistinctSubstring(input, 4)
    }

    fun part2(input: String): Int {
        return indexOfDistinctSubstring(input, 14)
    }

    val input = readWholeInput("Day06")

    println(part1(input))
    println(part2(input))
}





