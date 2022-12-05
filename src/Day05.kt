fun main() {
    data class Move(val count: Int, val source: Int, val target: Int)
    class CrateStacks(input: List<String>) {
        val stacks = mutableMapOf<Int, ArrayDeque<Char>>()
        val moves = mutableListOf<Move>()

        init {
            var parseMoves = false
            input.forEach { line ->
                if (line.isBlank()) {
                    parseMoves = true
                    return@forEach
                }
                if (parseMoves) {
                    addMove(line)
                    return@forEach
                }
                parseStacks(line)
            }
        }

        fun addMove(line: String) {
            val (c, s, t) = line.split("move ", " from ", " to ").mapNotNull { it.toIntOrNull() }
            moves.add(Move(c, s, t))
        }

        fun parseStacks(line: String) {
            line.forEachIndexed { index, c ->
                if (index % 4 != 1) return@forEachIndexed
                if (c.isDigit()) return
                if (!c.isUpperCase()) return@forEachIndexed
                val stack = index / 4 + 1
                ArrayDeque<String>()
                stacks.compute(stack) { _, v ->
                    val ad = v ?: ArrayDeque()
                    ad.add(c)
                    return@compute ad
                }
            }
        }

        fun doMovesSingle(): CrateStacks {
            moves.forEach { executeMoveSingle(it) }
            return this
        }

        fun doMovesGrouped(): CrateStacks {
            moves.forEach { executeMoveGrouped(it) }
            return this
        }

        private fun executeMoveSingle(move: Move) {
            repeat(move.count) {
                val crate = stacks[move.source]!!.removeFirst()
                stacks[move.target]!!.addFirst(crate)
            }
        }

        private fun executeMoveGrouped(move: Move) {
            for (i in 0 until move.count) {
                val crate = stacks[move.source]!!.removeFirst()
                stacks[move.target]!!.add(i, crate)
            }
        }

        fun getTop(): String {
            return stacks.keys.sorted().mapNotNull { stacks[it]?.first() }.joinToString("")
        }
    }

    fun part1(input: List<String>): String {
        return CrateStacks(input).doMovesSingle().getTop()
    }

    fun part2(input: List<String>): String {
        return CrateStacks(input).doMovesGrouped().getTop()

    }

    val input = readInput("Day05")

    println(part1(input))
    println(part2(input))
}





