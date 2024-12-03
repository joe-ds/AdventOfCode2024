import kotlin.math.abs

class Day02: Day(2) {
    private val input: List<List<Int>> = this.getInputAsLines().map { line ->
        line.split(' ').map { it.toInt() }
    }

    private fun countValid(ls: List<List<Int>>): Int {
        var count = 0
        for (row in ls) {
            var descending: Boolean? = null
            for (i in 0..row.lastIndex) {
                if (i == row.lastIndex) {
                    count++
                    break
                }

                val x = row[i] - row[i+1]

                if (abs(x) !in 1..3) break

                if (descending == null) {
                    descending = x < 0
                } else {
                    if (descending == true && (x > 0)) break
                    else if (descending == false && (x < 0)) break
                    else continue
                }
            }
        }
        return count
    }

    override fun partOne(): String {
        val count = countValid(input)
        return count.toString()
    }

    private fun threeOptions(row: List<Int>, i: Int): Boolean {
        val optionOne = with(row.toMutableList()) {
            this.removeAt(i)
            countValid(listOf(this)) == 1
        }

        val optionTwo = with(row.toMutableList()) {
            this.removeAt(i+1)
            countValid(listOf(this)) == 1
        }

        val optionThree = try {
            with(row.toMutableList()) {
                this.removeAt(i-1)
                countValid(listOf(this)) == 1
            }
        } catch (e: IndexOutOfBoundsException) {
            false
        }

        return optionOne || optionTwo || optionThree
    }

    override fun partTwo(): String {
        var count = 0

        for (row in input) {
            var descending: Boolean? = null
            for (i in 0..row.lastIndex) {
                if (i == row.lastIndex) {
                    count++
                    continue
                }

                val x = row[i] - row[i+1]

                if (abs(x) !in 1..3) {
                    if (threeOptions(row, i)) count++
                    break
                }

                if (descending == null) {
                    descending = x < 0
                } else {
                    if (descending == true && (x > 0)) {
                        if (threeOptions(row, i)) count++
                        break
                    }
                    else if (descending == false && (x < 0)) {
                        if (threeOptions(row, i)) count++
                        break
                    }
                    else continue
                }
            }
        }
        return count.toString()
    }

}