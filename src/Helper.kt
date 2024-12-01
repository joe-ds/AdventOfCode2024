import java.io.File

abstract class Day(val dNo: Int) {
    open fun partOne(): String = "Part One not implemented yet."
    open fun partTwo(): String = "Part Two not implemented yet."
}

fun Day.getInputAsLines(): List<String> {
    val filename: String = "inputs/" + (this.dNo.toString()).padStart(2, '0')
    return File(filename).readLines().map{ it.trim() }
}

fun Day.print() {
    println("Day ${this.dNo}:\n\tPart One: ${this.partOne()}\n\tPart Two: ${this.partTwo()}\n\n")
}