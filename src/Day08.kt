import kotlin.math.max

fun main() {

    fun part1(input: List<String>): Int {
        val visible = mutableSetOf<Coord2D>()
        val grid = input.map { line -> line.map { it.digitToInt() } }
        val lastX = grid.lastIndex
        val lastY = grid.first().lastIndex
        visible.addAll(grid.indices.map { Coord2D(it, 0) })
        visible.addAll(grid.indices.map { Coord2D(it, lastX) })
        visible.addAll(grid.first().indices.map { Coord2D(0, it) })
        visible.addAll(grid.last().indices.map { Coord2D(lastY, it) })

        var max: Int
        for (x in 1 until lastX) {
            max = -1

            for (y in grid[x].indices) {
                if (grid[x][y] > max) {
                    max = grid[x][y]
                    visible.add(Coord2D(x, y))
                }
                if (max == 9) break
            }
            max = -1
            for (y in grid[x].indices.reversed()) {
                if (grid[x][y] > max) {
                    max = grid[x][y]
                    visible.add(Coord2D(x, y))
                }
                if (max == 9) break
            }
        }
        for (y in 1 until lastY) {
            max = -1
            for (x in grid.indices) {
                if (grid[x][y] > max) {
                    max = grid[x][y]
                    visible.add(Coord2D(x, y))
                }
                if (max == 9) break
            }
            max = -1
            for (x in grid.indices.reversed()) {
                if (grid[x][y] > max) {
                    max = grid[x][y]
                    visible.add(Coord2D(x, y))
                }
                if (max == 9) break
            }
        }
        return visible.size
    }
    
    fun lowerIdxScore(grid: List<List<Int>>, a: Int, b: Int): Int {
        val height = grid[a][b]
        for (i in a - 1 downTo 0) {
            if (grid[i][b] >= height) return a - i
        }
        return a
    }

    fun scenicScore(grid: List<List<Int>>, x: Int, y: Int): Int {
        val height = grid[x][y]
        val scores = mutableListOf<Int>()
        var score = 0
        for (a in x - 1 downTo 0) {
            if (grid[a][y] >= height) {
                score = x - a
                break
            }
        }
        if (score > 0) scores.add(score)
        else scores.add(x)
        score = 0
        for (a in x + 1..grid.lastIndex) {
            if (grid[a][y] >= height) {
                score = a - x
                break
            }
        }
        if (score > 0) scores.add(score)
        else scores.add(grid.lastIndex - x)
        score = 0
        for (b in y - 1 downTo 0) {
            if (grid[x][b] >= height) {
                score = y - b
                break
            }
        }
        if (score > 0) scores.add(score)
        else scores.add(y)
        score = 0
        for (b in y + 1..grid[x].lastIndex) {
            if (grid[x][b] >= height) {
                score = b - y
                break
            }
        }
        if (score > 0) scores.add(score)
        else scores.add(grid[x].lastIndex - y)
        return scores.product()
    }

    fun part2(input: List<String>): Int {
        val grid = input.map { line -> line.map { it.digitToInt() } }
        var maxScore = 0
        grid.forEachIndexed { x, line ->
            line.indices.forEach { y ->
                val score = scenicScore(grid, x, y)
                maxScore = max(maxScore, score)
            }
        }
        return maxScore
    }

    val input = readInput("Day08")

    println(part1(input))
    println(part2(input))
}