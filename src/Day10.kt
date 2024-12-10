class Day10: Day(10) {
    val input = this.getInputAsLines().map {
        it.map(Char::digitToInt)
    }
    private val trails = emptyList<List<Coords>>().toMutableList()

    init {
        for (i in input.indices) {
            for (j in input.first().indices) {
                if (input[i][j] == 0) trails.add(input.getTrails(Coords(i, j)))
            }
        }
    }

    private fun List<List<Int>>.getPotentials(start: Coords): List<Coords> {
        val beforeCoords = Pair(start.first - 1, start.second)
        val afterCoords = Pair(start.first + 1, start.second)
        val aboveCoords = Pair(start.first, start.second - 1)
        val belowCoords = Pair(start.first, start.second + 1)

        return listOf(
            beforeCoords,
            afterCoords,
            aboveCoords,
            belowCoords).filter {
            (try { this.getWithCoord(it) } catch (e:IndexOutOfBoundsException) { -1 } != -1)
        }
    }

    private fun List<List<Int>>.getTrails(start: Coords): List<Coords> {
        val nines = emptyList<Coords>().toMutableList()
        var current: Coords = start
        val stack = emptyList<Coords>().toMutableList()

        while (true) {
            val height = this[current.first][current.second]
            if (height == 9) {
                nines.add(current)
                current = if (stack.isNotEmpty()) stack.removeFirst() else break
            } else {
                val potentials = this.getPotentials(current).filter { this.getWithCoord(it) == height + 1 }
                if (potentials.isEmpty()) {
                    current = if (stack.isNotEmpty()) stack.removeFirst() else break
                } else if (potentials.size == 1) {
                    current = potentials.first()
                } else {
                    current = potentials.first()
                    potentials.drop(1).map { stack.add(it) }
                }
            }
        }
        return nines
    }

    override fun partOne(): String = trails.sumOf { it.toSet().size }.toString()

    override fun partTwo(): String = trails.sumOf { it.size }.toString()
}