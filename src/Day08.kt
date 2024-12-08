class Day08: Day(8) {
    private val input = this.getInputAsLines()
    private val frequencies = getFrequencies(input)

    private fun getFrequencies(rawInput: List<String>):
            Map<Char, MutableList<Pair<Int, Int>>> {
        val frequencies = emptyMap<Char, MutableList<Pair<Int, Int>>>().toMutableMap()
        for (i in rawInput.indices) {
            val row = rawInput[i].toCharArray()
            for (j in row.indices) {
                val c = row[j]
                if (c == '.') continue
                else {
                    if (frequencies.keys.contains(c)) frequencies[c]!!.add(Pair(i, j))
                    else frequencies[c] = listOf(Pair(i, j)).toMutableList()
                }
            }
        }
        return frequencies
    }

    private fun Pair<Int, Int>.getAntinodes(other: Pair<Int, Int>): Pair<Int, Int> {
        return Pair(
            this.first + (this.first - other.first),
            this.second + (this.second - other.second)
            )
    }

    override fun partOne(): String {
        val antinodes = emptySet<Pair<Int, Int>>().toMutableSet()

        frequencies.values.map { frequencyCoords ->
            for (i in 0..<frequencyCoords.lastIndex) {
                val current = frequencyCoords[i]
                for (j in i+1..frequencyCoords.lastIndex) {
                    for (pair in listOf(
                        current.getAntinodes(frequencyCoords[j]),
                        frequencyCoords[j].getAntinodes(current)))
                    {
                        try {
                            input[pair.first][pair.second]
                            antinodes.add(pair)
                        } catch (e: IndexOutOfBoundsException) {
                            continue
                        }
                    }
                }
            }
        }
        return antinodes.size.toString()
    }

    override fun partTwo(): String {
        val antinodes = emptySet<Pair<Int, Int>>().toMutableSet()

        frequencies.values.map { frequencyCoords ->
            for (i in 0..<frequencyCoords.lastIndex) {
                val current = frequencyCoords[i]
                for (j in i+1..frequencyCoords.lastIndex) {
                    var leap = current
                    var frog = frequencyCoords[j]
                    antinodes.add(leap)
                    antinodes.add(frog)
                    // Let's go in one direction as far as possible.
                    while (true) {
                        val leapfrog = leap.getAntinodes(frog)
                        try {
                            input[leapfrog.first][leapfrog.second]
                            antinodes.add(leapfrog)
                            frog = leap.also {
                                leap = leapfrog
                            }
                        } catch (e: IndexOutOfBoundsException) {
                            break
                        }
                    }
                    // And now in the other direction!
                    leap = frequencyCoords[j]
                    frog = current
                    while (true) {
                        val leapfrog = leap.getAntinodes(frog)
                        try {
                            input[leapfrog.first][leapfrog.second]
                            antinodes.add(leapfrog)
                            frog = leap.also {
                                leap = leapfrog
                            }
                        } catch (e: IndexOutOfBoundsException) {
                            break
                        }
                    }
                }
            }
        }
        return antinodes.size.toString()
    }
}