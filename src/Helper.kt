import java.io.File

abstract class Day(val dNo: Int) {
    open fun partOne(): String = "Part One not implemented yet."
    open fun partTwo(): String = "Part Two not implemented yet."

    override fun toString(): String {
        return "Day ${this.dNo}:\n\tPart One: ${this.partOne()}\n\tPart Two: ${this.partTwo()}\n\n"
    }
}

fun Day.getInputAsLines(): List<String> {
    val filename: String = "inputs/" + (this.dNo.toString()).padStart(2, '0')
    return File(filename).readLines().map{ it.trim() }
}

typealias Coords = Pair<Int, Int>

fun <T> List<List<T>>.getWithCoord(coords: Coords): T = this[coords.first][coords.second]

operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>) = Pair(this.first + other.first, this.second + other.second)
operator fun Pair<Int, Int>.minus(other: Pair<Int, Int>) = Pair(this.first + other.first, this.second + other.second)

fun Day.getInput(): String {
    val filename: String = "inputs/" + (this.dNo.toString()).padStart(2, '0')
    return File(filename).readText().trim()
}