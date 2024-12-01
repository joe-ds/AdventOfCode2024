import kotlin.math.abs

class Day01: Day(1) {
    private val input = this.getInputAsLines().map { it.split("\\s+".toRegex()) }
    private val listOne = emptyList<Int>().toMutableList()
    private val listTwo = emptyList<Int>().toMutableList()

    init {
        input.map {
            listOne.add(it.first().toInt())
            listTwo.add(it.last().toInt())
        }
        listOne.sort()
        listTwo.sort()
    }

    override fun partOne(): String {
        return listOne.zip(listTwo).sumOf {
            abs(it.first - it.second)
        }.toString()
    }

    override fun partTwo(): String {
        return listOne.sumOf { e ->
            e * listTwo.count { it == e }
        }.toString()
    }
}