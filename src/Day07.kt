fun main() {


    fun buildFileSystem(input: List<String>): Directory {
        val root = Directory(null, "/")
        var currentDir: Directory = root
        val iter = input.iterator()
        while (iter.hasNext()) {
            val line = iter.next()
            when {
                line == "$ cd /" -> currentDir = root
                line == "$ ls" -> continue
                line == "$ cd .." -> currentDir = currentDir.parent!!
                line.startsWith("$ cd") -> currentDir = currentDir.cd(line)
                else -> currentDir.new(line)
            }
        }
        return root
    }

    fun part1(input: Directory): Long {
        return input.sumOfUnderSize(100_000)
    }

    fun part2(input: Directory): Long? {
        // 70M: available, free 30M required
        val missingSpace = 30_000_000 - 70_000_000L + input.size
        return input.smallestDirBigger(missingSpace)
    }

    val input = buildFileSystem(readInput("Day07"))

    println(part1(input))
    println(part2(input))
}

interface FileSystem {
    fun sumOfUnderSize(sizeLimit: Long): Long
    fun smallestDirBigger(minimum: Long): Long?

    val parent: Directory?
    val name: String
    val size: Long
}

class Day07File(override val parent: Directory, override val name: String, override val size: Long) : FileSystem {
    override fun sumOfUnderSize(sizeLimit: Long): Long = 0L
    override fun smallestDirBigger(minimum: Long) = null // we don't want a file

}

class Directory(override val parent: Directory?, override val name: String) : FileSystem {
    override val size: Long
        get() = content.values.sumOf { it.size }

    private val content: MutableMap<String, FileSystem> = mutableMapOf()

    operator fun get(line: String): FileSystem {
        return content[line.removePrefix("$ cd ")] ?: error("$name has no such file: $line")
    }

    fun cd(line: String): Directory {
        val file = this[line]
        return if (file is Directory) file else error("$name has no such dir: $line")
    }

    fun new(line: String) {
        when {
            line.startsWith("dir") -> {
                val newName = line.removePrefix("dir ")
                content.putIfAbsent(newName, Directory(this, newName))
            }

            line.startsWith('$') -> error("bad new file $line")
            else -> {
                val (size, newName) = line.split(' ', limit = 2)
                content.putIfAbsent(newName, Day07File(this, newName, size.toLong()))
            }
        }
    }

    override fun sumOfUnderSize(sizeLimit: Long): Long {
        return content.values.sumOf { it.sumOfUnderSize(sizeLimit) } + if (size <= sizeLimit) size else 0
    }

    override fun smallestDirBigger(minimum: Long): Long? {
        if (this.size < minimum) return null // we could filter it instead using null, but that's good enough
        return content.values
            .mapNotNull { it.smallestDirBigger(minimum) }
            .minOrNull()
            ?: this.size
    }
}



