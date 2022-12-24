import java.io.File
import java.math.BigInteger
import java.security.MessageDigest
import kotlin.math.abs

typealias Vector2D = Coord2D

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt")
    .readLines()

/**
 * Read whole input txt file as one string.
 */
fun readWholeInput(name: String) = File("src", "$name.txt")
    .readText()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

data class Coord2D(val x: Int, val y: Int) {
    fun adjacentTo(coord2D: Coord2D): Boolean {
        return abs(x - coord2D.x) <= 1 && abs(y - coord2D.y) <= 1
    }

    operator fun minus(other: Coord2D): Coord2D {
        return Coord2D(this.x - other.x, this.y - other.y)
    }

    operator fun plus(other: Coord2D): Coord2D {
        return Coord2D(this.x + other.x, this.y + other.y)
    }

    fun moved(byX: Int, byY: Int): Coord2D {
        return Coord2D(x + byX, y + byY)
    }

    fun manhattenDistance(other: Vector2D): Int {
        return abs(this.x - other.x) + abs(this.y - other.y)
    }
}

/**
 * Returns the product (multiplication) of all elements in the collection.
 */
fun List<Int>.product(): Int {
    return this.reduce { acc, i -> acc * i }
}

/**
 * All permutations of a list
 * Source: https://rosettacode.org/wiki/Permutations#Kotlin
 */
fun <T> permute(input: List<T>): List<List<T>> {
    if (input.size == 1) return listOf(input)
    val perms = mutableListOf<List<T>>()
    val toInsert = input[0]
    for (perm in permute(input.drop(1))) {
        for (i in 0..perm.size) {
            val newPerm = perm.toMutableList()
            newPerm.add(i, toInsert)
            perms.add(newPerm)
        }
    }
    return perms
}