fun main() {
    fun part1(input: List<Int>): Int {
        return input.maxOrNull()!!
    }

    fun part2(input: List<Int>): Int {
        return input.sortedDescending().subList(0,3).sum()
    }

    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day01_test")
//    check(part1(testInput) == 1)

    val input = mutableListOf<Int>()
    var currentCalories = 0
    readInput("Day01").forEach { calories ->
        if (calories.isBlank()) {
            input.add(currentCalories)
            currentCalories = 0
        } else {
            currentCalories += calories.toInt()
        }
    }

    println(part1(input))
    println(part2(input))
}
