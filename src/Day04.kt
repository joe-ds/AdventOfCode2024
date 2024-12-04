class Day04: Day(4) {
    private val input = this.getInputAsLines().map {
        it.toCharArray().toList()
    }

    override fun partOne(): String {
        var count = 0
        val check = "XMAS".toCharArray().toList()
        val rowLimit = input.lastIndex
        val colLimit = input.first().lastIndex
        for (row in input.indices) {
            for (col in input.first().indices) {
                // <--X
                if (input[row][col] == 'X' && col > 2) {
                    if (input[row].subList(col-3, col+1).reversed() == check) {
                        count++
                    }
                }

                // X-->
                if (input[row][col] == 'X' && (col+3) <= colLimit) {
                    if (input[row].subList(col, col+4) == check) {
                        count++
                    }
                }

                // ^
                // |
                // |
                // X
                if (input[row][col] == 'X' && row >= 3) {
                    val against = listOf(
                        input[row][col],
                        input[row-1][col],
                        input[row-2][col],
                        input[row-3][col]

                    )
                    if (against == check) {
                        count++
                    }
                }

                // X
                // |
                // |
                // v
                if (input[row][col] == 'X' && (row+3) <= rowLimit) {
                    val against = listOf(
                        input[row][col],
                        input[row+1][col],
                        input[row+2][col],
                        input[row+3][col]

                    )
                    if (against == check) {
                        count++
                    }
                }

                // ^
                //  \
                //   \
                //    X
                if (input[row][col] == 'X' && col >= 3 && row >= 3) {
                    val against = listOf(
                        input[row][col],
                        input[row-1][col-1],
                        input[row-2][col-2],
                        input[row-3][col-3]

                    )
                    if (against == check) {
                        count++
                    }
                }

                //    ^
                //   /
                //  /
                // X
                if (input[row][col] == 'X' && (col+3) <= colLimit && row >= 3) {
                    val against = listOf(
                        input[row][col],
                        input[row-1][col+1],
                        input[row-2][col+2],
                        input[row-3][col+3]

                    )
                    if (against == check) {
                        count++
                    }
                }

                //    X
                //   /
                //  /
                // v
                if (input[row][col] == 'X' && col > 2 && (row+3) <= rowLimit) {
                    val against = listOf(
                        input[row][col],
                        input[row+1][col-1],
                        input[row+2][col-2],
                        input[row+3][col-3]

                    )
                    if (against == check) {
                        count++
                    }
                }

                // X
                //  \
                //   \
                //    v
                if (input[row][col] == 'X' && (col+3) <= input.first().lastIndex && (row+3) <= input.lastIndex) {
                    val against = listOf(
                        input[row][col],
                        input[row+1][col+1],
                        input[row+2][col+2],
                        input[row+3][col+3]

                    )
                    if (against == check) {
                        count++
                    }
                }
            }
        }
        return count.toString()
    }

    override fun partTwo(): String {
        var count = 0
        for (row in input.indices.drop(1)) {
            for (col in input.first().indices.drop(1)) {
                if (input[row][col] == 'A' && row < input.lastIndex && col < input.first().lastIndex) {
                    val against = listOf(
                        listOf(input[row-1][col-1], input[row+1][col+1]).containsAll(listOf('S', 'M')),
                        listOf(input[row-1][col+1], input[row+1][col-1]).containsAll(listOf('S', 'M')),
                    ).all { it }
                    if (against) count++
                }
            }
        }
        return count.toString()
    }
}