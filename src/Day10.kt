fun main() {
    /**
     * cathode-ray tube screen and simple CPU
     */
    class CRT {
        var cycle = 0
        var register = 1
        var row = mutableListOf<Char>()


        fun exec(op: String) {
            doCycle()
            if ("noop" == op) return
            doCycle()
            register += op.split(" ")[1].toInt()
        }

        private fun doCycle() {
            cycle++
            drawPixel()
        }

        private fun drawPixel() {
            val idx = row.size
            if (idx >= register - 1 && idx <= register + 1)
                row.add('#')
            else
                row.add('.')
            if (cycle % 40 == 0) {
                println(row.joinToString(" ")) // a bit easier to read with spaces
                row.clear()
            }
        }
    }


    fun part1(input: List<String>): Int {
        val signals = mutableListOf<Int>()
        var register = 1
        var cycle = 0
        input.forEach { line ->
            if ("noop" == line) {
                cycle++
                if ((cycle - 20) % 40 == 0) signals.add(cycle * register)
                return@forEach
            }
            cycle++
            if ((cycle - 20) % 40 == 0) signals.add(cycle * register)
            cycle++
            if ((cycle - 20) % 40 == 0) signals.add(cycle * register)
            register += line.split(" ")[1].toInt()
        }
        return signals.sum()
    }

    fun part2(input: List<String>): Int {
        val crt = CRT()
        input.forEach { line ->
            crt.exec(line)
        }
        return 0
    }

    val input = readInput("Day10")

    println(part1(input))
    println(part2(input))
}


