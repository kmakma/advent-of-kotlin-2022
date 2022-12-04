fun main() {
    fun myScore(strategy: String): Int {
        return when {
            strategy.contains('X') -> 1
            strategy.contains('Y') -> 2
            strategy.contains('Z') -> 3
            else -> throw IllegalArgumentException()
        }
    }

    fun part1(input: List<String>): Int {
        val wins = listOf("A Y", "B Z", "C X")
        val draws = listOf("A X", "B Y", "C Z")
        var score = 0
        input.forEach {
            score += myScore(it)
            when {
                wins.contains(it) -> score += 6
                draws.contains(it) -> score += 3
            }
        }
        return score
    }

    fun lose(theirs: Char): Int {
        return when (theirs) {
            'A' -> myScore("Z")
            'B' -> myScore("X")
            'C' -> myScore("Y")
            else -> throw IllegalArgumentException()
        }
    }

    fun draw(theirs: Char): Int {
        return 3 + when (theirs) {
            'A' -> myScore("X")
            'B' -> myScore("Y")
            'C' -> myScore("Z")
            else -> throw IllegalArgumentException()
        }
    }

    fun win(theirs: Char): Int {
        return 6 + when (theirs) {
            'A' -> myScore("Y")
            'B' -> myScore("Z")
            'C' -> myScore("X")
            else -> throw IllegalArgumentException()
        }
    }

    fun part2(input: List<String>): Int {
        var score = 0
        input.forEach {
            when (it[2]) {
                'X' -> {
                    score += lose(it[0])
                }

                'Y' -> {
                    score += draw(it[0])
                }

                'Z' -> {
                    score += win(it[0])
                }
            }
        }
        return score
    }

    val input = readInput("Day02")


    println(part1(input))
    println(part2(input))
}