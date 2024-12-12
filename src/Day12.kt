class Day12: Day(12) {
    val input = this.getInputAsLines()

    private fun Coords.next(xs: List<String>): Coords {
        return if (this.second == xs.first().lastIndex) {
            if (this.first == xs.lastIndex) {
                Coords(0, 0)
            } else { Coords(this.first + 1, 0) }
        } else {
            Coords(this.first, this.second + 1)
        }
    }

    private fun List<String>.getWithCoord(coords: Coords): Char = this[coords.first][coords.second]

    override fun partOne(): String {
        val visited = emptySet<Coords>().toMutableSet()
        var total: Long = 0
        var perimeter = 0
        var area = 0
        val stack = emptySet<Coords>().toMutableSet()
        var current = Coords(0, 0)

        while(visited.size < (input.size * input.first().length)) {
            if (visited.contains(current)) {
                current = if (stack.isEmpty()) {
                    current.next(input)
                } else {
                    stack.first().also { stack.remove(it) }
                }
            } else {
                val here = input.getWithCoord(current)
                visited.add(current)
                area++

                if (current.first > 0) {
                    val x = Coords(current.first - 1, current.second)
                    val y = input.getWithCoord(x)
                    if (y != here) perimeter++
                    if (!visited.contains(x) && y == here) stack.add(x)
                } else {
                    perimeter++
                }

                if (current.first < input.first().lastIndex) {
                    val x = Coords(current.first + 1, current.second)
                    val y = input.getWithCoord(x)
                    if (y != here) perimeter++
                    if (!visited.contains(x) && y == here) stack.add(x)
                } else {
                    perimeter++
                }

                if (current.second > 0) {
                    val x = Coords(current.first, current.second - 1)
                    val y = input.getWithCoord(x)
                    if (y != here) perimeter++
                    if (!visited.contains(x) && y == here) stack.add(x)
                } else {
                    perimeter++
                }

                if (current.second < input.lastIndex) {
                    val x = Coords(current.first, current.second + 1)
                    val y = input.getWithCoord(x)
                    if (y != here) perimeter++
                    if (!visited.contains(x) && y == here) stack.add(x)
                } else {
                    perimeter++
                }

                if (stack.isEmpty()) {
                    total += perimeter * area
                    perimeter = 0
                    area = 0
                    current = current.next(input)
                } else {
                    current = stack.first().also { stack.remove(it) }
                }
            }
        }
        return total.toString()
    }

    override fun partTwo(): String {
        val visited = emptySet<Coords>().toMutableSet()
        var total: Long = 0
        var perimeter = 0
        var area = 0
        val stack = emptySet<Coords>().toMutableSet()
        var current = Coords(0, 0)

        while(visited.size < (input.size * input.first().length)) {
            if (visited.contains(current)) {
                current = if (stack.isEmpty()) {
                    current.next(input)
                } else {
                    stack.first().also { stack.remove(it) }
                }
            } else {
                val here = input.getWithCoord(current)
                visited.add(current)
                area++
                var above = false
                var below = false
                var before = false
                var after = false

                if (current.first > 0) {
                    val x = Coords(current.first - 1, current.second)
                    val y = input.getWithCoord(x)
                    if (!visited.contains(x) && y == here) stack.add(x)
                    if (y == here) above = true
                }

                if (current.first < input.first().lastIndex) {
                    val x = Coords(current.first + 1, current.second)
                    val y = input.getWithCoord(x)
                    if (!visited.contains(x) && y == here) stack.add(x)
                    if (y == here) below = true
                }

                if (current.second > 0) {
                    val x = Coords(current.first, current.second - 1)
                    val y = input.getWithCoord(x)
                    if (!visited.contains(x) && y == here) stack.add(x)
                    if (y == here) before = true
                }

                if (current.second < input.lastIndex) {
                    val x = Coords(current.first, current.second + 1)
                    val y = input.getWithCoord(x)
                    if (!visited.contains(x) && y == here) stack.add(x)
                    if (y == here) after = true
                }

                val topLeft = try {
                    input.getWithCoord(current + Coords(-1, -1)) == here
                } catch (_:IndexOutOfBoundsException) { false }
                val topRight = try {
                    input.getWithCoord(current + Coords(-1, 1)) == here
                } catch (_:IndexOutOfBoundsException) { false }
                val bottomLeft = try {
                    input.getWithCoord(current + Coords(1, -1)) == here
                } catch (_:IndexOutOfBoundsException) { false }
                val bottomRight = try {
                    input.getWithCoord(current + Coords(1, 1)) == here
                } catch (_:IndexOutOfBoundsException) { false }

                perimeter += listOf(
                    (before && above && !topLeft) || !before && !above,
                    (after && above && !topRight) || !after && !above,
                    (before && below && !bottomLeft) || !before && !below,
                    (after && below && !bottomRight) || !after && !below,
                ).filter { it }.size

                if (stack.isEmpty()) {
                    total += perimeter * area
                    perimeter = 0
                    area = 0
                    current = current.next(input)
                } else {
                    current = stack.first().also { stack.remove(it) }
                }
            }
        }
        return total.toString()
    }
}