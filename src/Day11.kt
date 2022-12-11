import kotlin.properties.Delegates

fun main() {

    class Monkey(val map: Map<Int, Monkey>) {
        lateinit var operation: (Long) -> Long
        lateinit var divisibleTest: (Long) -> Boolean
        var divisor by Delegates.notNull<Int>()
        var isDivisibleTarget by Delegates.notNull<Int>()
        var notDivisibleTarget by Delegates.notNull<Int>()

        var useRelief = true
        val items = mutableListOf<Long>()
        var inspected = 0

        fun addItems(newItems: List<Long>) {
            items.addAll(newItems)
        }

        fun inspectAll() {
            while (items.isNotEmpty()) {
                inspectItem()
            }
        }

        private fun inspectItem() {
            var worry = operation(items.removeFirst())
            if (useRelief) worry /= 3
            worry %= divisor
            inspected++
            if (divisibleTest(worry)) {
                map[isDivisibleTarget]!!.addItem(worry)
            } else {
                map[notDivisibleTarget]!!.addItem(worry)
            }
        }

        private fun addItem(item: Long) {
            items.add(item)
        }

    }

    fun parseNewMonkey(monkeyMap: MutableMap<Int, Monkey>, line: String): Monkey {
        val id = line[line.lastIndex - 1].digitToInt()
        val monkey = Monkey(monkeyMap)
        monkeyMap[id] = monkey
        return monkey
    }

    fun parseItems(monkey: Monkey, line: String) {
        monkey.addItems(line.removePrefix("  Starting items: ")
            .split(", ")
            .map { it.toLong() })
    }


    fun parseOperation(monkey: Monkey, line: String) {
        val operation = line.removePrefix("  Operation: ")
            .split(" = ")[1]
            .split(" ")
        val op: (Long) -> Long = if (operation[2] == "old") {
            if (operation[1] == "+") {
                { old: Long -> old + old }
            } else {
                { old: Long -> old * old }
            }
        } else {
            if (operation[1] == "+") {
                { old: Long -> operation[2].toInt() + old }
            } else {
                { old: Long -> operation[2].toInt() * old }
            }
        }
        monkey.operation = op
    }

    fun parseTest(monkey: Monkey, line: String) {
        val divisibleBy = line.removePrefix("  Test: divisible by ").toInt()
        monkey.divisor = divisibleBy
        val test = { worry: Long -> worry % divisibleBy == 0L }
        monkey.divisibleTest = test
    }

    fun parseTrue(monkey: Monkey, line: String) {
        monkey.isDivisibleTarget = line.last().digitToInt()
    }

    fun parseFalse(monkey: Monkey, line: String) {
        monkey.notDivisibleTarget = line.last().digitToInt()
    }

    fun parseMonkeys(input: List<String>): List<Monkey> {
        val monkeyMap = mutableMapOf<Int, Monkey>()
        lateinit var monkey: Monkey
        input.forEach { line ->
            when {
                line.contains("Monkey") -> monkey = parseNewMonkey(monkeyMap, line)
                line.contains("Starting") -> parseItems(monkey, line)
                line.contains("Operation") -> parseOperation(monkey, line)
                line.contains("Test") -> parseTest(monkey, line)
                line.contains("If true") -> parseTrue(monkey, line)
                line.contains("If false") -> parseFalse(monkey, line)
            }
        }
        return monkeyMap.toSortedMap().values.toList()
    }

    fun part1(input: List<String>): Int {
        val monkeys = parseMonkeys(input)
        val commonDivisor = monkeys.fold(1) { acc, monkey -> acc * monkey.divisor }
        monkeys.forEach { it.divisor = commonDivisor }
        repeat(20) {
            monkeys.forEach {
                it.inspectAll()
            }
        }

        return monkeys.map { it.inspected }
            .sorted()
            .takeLast(2)
            .product()
    }

    fun part2(input: List<String>): Long {
        val monkeys = parseMonkeys(input)
        val commonDivisor = monkeys.fold(1) { acc, monkey -> acc * monkey.divisor }
        monkeys.forEach {
            it.divisor = commonDivisor
            it.useRelief = false
        }
        repeat(10000) {
            monkeys.forEach {
                it.inspectAll()
            }
        }
        return monkeys.map { it.inspected }
            .sorted()
            .takeLast(2)
            .fold(1L) { acc, i -> acc * i }
    }

    val input = readInput("Day11")

    println(part1(input))
    println(part2(input))
}



