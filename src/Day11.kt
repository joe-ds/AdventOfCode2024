import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.pow

class Day11: Day(11) {
    private val inputs = this.getInput().split(" ").map { it.toLong() }

    private fun List<Long>.compress(): Map<Long, Long> = this.associateWith {
        e -> this.count { it == e }.toLong()
    }

    private fun MutableMap<Long, Long>.add(key: Long, value: Long) {
        if (this.keys.contains(key)) {
            this[key] = this[key]!! + value
        } else {
            this[key] = value
        }
    }

    private fun MutableMap<Long, Long>.subtract(key: Long, value: Long) {
        this[key] = this[key]!! - value
    }

    private fun MutableList<Long>.repeatBlink(r: Int): Long {
        val result = this.compress().toMutableMap()

        repeat(r) {
            val next = result.toMutableMap()

            for ((key, value) in result) {
                if (value == 0L) continue

                if (key == 0L) {
                    next.add(1, value)
                } else {
                    val digits = (floor(log10(key.toDouble())) + 1).toInt()
                    val digitPair = Pair(
                        key / (10.0.pow(digits / 2)).toInt(),
                        key % (10.0.pow(digits / 2)).toInt()
                    )
                    if (digits % 2 == 0) {
                        next.add(digitPair.first, value)
                        next.add(digitPair.second, value)
                    } else {
                        next.add(key * 2024, value)
                    }
                }
                next.subtract(key, value)
            }
            result.clear()
            result.putAll(next.filterValues { it > 0 })
        }
        return result.values.sum()
    }

    override fun partOne(): String = inputs.toMutableList().repeatBlink(25).toString()
    override fun partTwo(): String = inputs.toMutableList().repeatBlink(75).toString()
}