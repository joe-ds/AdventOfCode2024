class Day09: Day(9) {
    private val input = this.getInput().map(Char::digitToInt)
    private val processed = emptyList<Data>().toMutableList()

    enum class CLASSIFICATION {
        FREE,
        USED
    }

    data class Data(val classification: CLASSIFICATION, val id: Long?, var count: Int) {
        override fun toString(): String {
            return if (classification == CLASSIFICATION.FREE) {
                ".".repeat(count)
            } else {
                id.toString().repeat(count)
            }
        }
    }

    init {
        var free = false
        var count: Long = 0
        for (entry in input) {
            if (entry != 0) {
                processed.add(
                    if (free) Data(CLASSIFICATION.FREE, null, entry)
                    else Data(CLASSIFICATION.USED, count, entry).also { count++ }
                )
            }
            free = !free
        }
    }

    private fun List<Data>.compactFile(): List<Data> {
        val result = this.map { it.copy() }.toMutableList() // Create a deep copy of the list
        var currentIndex = 0

        while (true) {
            if (currentIndex > result.lastIndex) break
            val current = result[currentIndex]
            if (current.classification == CLASSIFICATION.FREE && current.count > 0) {
                val lastUsedBlockIndex = result.indexOfLast { it.classification == CLASSIFICATION.USED }

                if (lastUsedBlockIndex < currentIndex) break

                val lastUsedBlock = result[lastUsedBlockIndex]

                if (lastUsedBlock.count > current.count) {
                    result[currentIndex] = Data(lastUsedBlock.classification,
                        lastUsedBlock.id,
                        current.count)
                    lastUsedBlock.count -= current.count
                } else if (lastUsedBlock.count == current.count) {
                    result[currentIndex] = lastUsedBlock.also {
                        result[lastUsedBlockIndex] = current
                    }
                } else {
                    current.count -= lastUsedBlock.count
                    result.add(currentIndex, lastUsedBlock)
                    result.removeAt(lastUsedBlockIndex + 1)
                }
            }
            currentIndex++
        }
        return result
    }

    private fun List<Data>.neoCompact(): List<Data> {
        val result = this.map { it.copy() }.toMutableList() // Create a deep copy of the list
        var currentLargestId = this.maxByOrNull { it.id ?: 0 }!!.id!!

        while (currentLargestId > 0) {
            val currentLargestIdIndex = result.indexOfFirst { it.id == currentLargestId }
            val current = result[currentLargestIdIndex]
            val gapIndex = result.indexOfFirst {
                it.count >= current.count && it.classification == CLASSIFICATION.FREE
            }

            if (gapIndex != -1 && gapIndex < currentLargestIdIndex) {
                val gap = result[gapIndex]
                if (gap.count > current.count) {
                    result[gapIndex] = current.also { result[currentLargestIdIndex] = Data(
                        CLASSIFICATION.FREE,
                        null,
                        current.count
                    ) }
                    result.add(gapIndex+1, Data(
                        CLASSIFICATION.FREE,
                        null,
                        gap.count - current.count
                    ))
                } else {
                    result[gapIndex] = current.also { result[currentLargestIdIndex] = result[gapIndex] }
                }
            }
            currentLargestId--
        }
        return result
    }

    private fun List<Data>.checksum(): Long {
        var i = 0
        var sum: Long = 0

        for (data in this) {
            repeat(data.count) {
                if (data.classification == CLASSIFICATION.USED) sum += i * data.id!!
                i++
            }
        }
        return sum
    }

    override fun partOne(): String = processed.compactFile().checksum().toString()

    override fun partTwo(): String = processed.neoCompact().checksum().toString()
}