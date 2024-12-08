class Day06: Day(6) {
    private val rawInput = this.getInputAsLines().map {
        it.toCharArray().toList()
    }
    private val input = rawInput.map { row ->
        row.map {
            it == '#'
        }
    }
    private lateinit var start: Pair<Int, Int>

    enum class Direction(val adder: Pair<Int, Int>) {
        UP(Pair(-1, 0)),
        DOWN(Pair(1, 0)),
        LEFT(Pair(0, -1)),
        RIGHT(Pair(0, 1))
    }

    private fun Direction.next() = when(this) {
        Direction.UP -> Direction.RIGHT
        Direction.RIGHT -> Direction.DOWN
        Direction.DOWN -> Direction.LEFT
        Direction.LEFT -> Direction.UP
    }

    init {
        for (row in rawInput.indices) {
            val col = rawInput[row].indexOf('^')
            if (col != -1) {
                start = Pair(row, col)
                break
            }
        }
    }

    private fun getVisited(start: Pair<Int, Int>): Set<Pair<Int, Int>> {
        var direction = Direction.UP
        var current = start
        val visited = setOf(current).toMutableSet()

        while (true) {
            try {
                val next = current + direction.adder
                val ahead = input[next.first][next.second]
                if (ahead) {
                    direction = direction.next()
                } else {
                    current = next
                    visited.add(current)
                }
            } catch (e: IndexOutOfBoundsException) {
                break
            }
        }
        return visited
    }

    override fun partOne(): String = getVisited(start).size.toString()

    private fun isLoop(grid: List<List<Boolean>>, start: Pair<Int, Int>): Boolean {
        var direction = Direction.UP
        var current = start
        var count = 1
        val visited = setOf(current).toMutableSet()

        while (true) {
            try {
                val next = current + direction.adder
                val ahead = grid[next.first][next.second]
                if (ahead) {
                    direction = direction.next()
                } else {
                    current = next
                    visited.add(current)

                    // if the count is greater than twice the number of visited nodes, we're in a loop!
                    if (count > visited.size * 2) {
                        return true
                    } else {
                        count++
                    }
                }
            } catch (e: IndexOutOfBoundsException) {
                return false
            }
        }
    }

    override fun partTwo(): String {
        val visited = getVisited(start)
        val count: Int = visited.map {
            val trial = input.map(List<Boolean>::toMutableList).toMutableList()
            trial[it.first][it.second] = true
            if (isLoop(trial, start)) 1 else 0
        }.sum()
        return count.toString()
    }
}