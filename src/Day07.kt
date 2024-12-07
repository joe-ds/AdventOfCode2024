class Day07: Day(7) {
    private val input: Map<Long, List<Int>> = this.getInputAsLines().associate { line ->
        line.split(": ").let {
            it.first().toLong() to it.last().split(" ").map(String::toInt)
        }
    }

    private fun recurseThis(whatsLeft: List<Int>, target: Long, currentSum: Long): Boolean {
        val n = whatsLeft.first()
        val sum: Long = currentSum + n
        val prod: Long = currentSum * n

        return if (whatsLeft.size == 1) sum == target || prod == target
        else (sum <= target && recurseThis(whatsLeft.drop(1), target, sum) ||
                prod <= target && recurseThis(whatsLeft.drop(1), target, prod))
    }

    override fun partOne(): String {
        val answer = input.keys.filter { entry ->
            recurseThis(input[entry]!!.drop(1), entry, input[entry]!!.first().toLong())
        }.sum()

        return answer.toString()
    }

    private fun recurseThisAgain(whatsLeft: List<Int>, target: Long, currentSum: Long): Boolean {
        val n = whatsLeft.first()
        val sum: Long = currentSum + n
        val prod: Long = currentSum * n
        val cat: Long = "$currentSum$n".toLong()

        return if (whatsLeft.size == 1) listOf(sum, prod, cat).contains(target)
        else (sum <= target && recurseThisAgain(whatsLeft.drop(1), target, sum) ||
                prod <= target && recurseThisAgain(whatsLeft.drop(1), target, prod) ||
                cat <= target && recurseThisAgain(whatsLeft.drop(1), target, cat))
    }

    override fun partTwo(): String {
        val answer = input.keys.filter { entry ->
            recurseThisAgain(input[entry]!!.drop(1), entry, input[entry]!!.first().toLong())
        }.sum()
        return answer.toString()
    }
}