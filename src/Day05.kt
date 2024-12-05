class Day05: Day(5) {
    private val rawInput = this.getInputAsLines()
    private val rules = rawInput.takeWhile { it.isNotEmpty() }.map {
        val xy = it.split('|')
        Pair(xy.first().toInt(), xy.last().toInt())
    }
    private val updates = rawInput.dropWhile { it.isNotEmpty() }.drop(1).map {
        it.split(',').map { entry -> entry.toInt() }
    }

    private fun Pair<Int, Int>.applyRule(update: List<Int>): Boolean {
        val first = update.indexOf(this.first)
        val second = update.indexOf(this.second)
        return (first == -1 || second == -1) || first < second
    }

    private fun MutableList<Int>.swap(p: Pair<Int, Int>) {
        val first = this.indexOf(p.first)
        val second = this.indexOf(p.second)
        this[first] = this[second].also { this[second] = this[first] }
    }

    private fun List<Int>.getMiddle(): Int = this[this.size / 2]

    override fun partOne(): String {
        val answer = updates.filter { update ->
            rules.all { it.applyRule(update) }
        }.sumOf {
            it.getMiddle()
        }
        return answer.toString()
    }

    override fun partTwo(): String {
        val invalids = updates.filter { update ->
            rules.any { !it.applyRule(update) }
        }.map { it.toMutableList() }
        var modified = true

        while (modified) {
            modified = false

            val iter = invalids.listIterator()
            while (iter.hasNext()) {
                val update = iter.next()
                val invalidRules = rules.filter { !it.applyRule(update) }
                if (invalidRules.isNotEmpty()) modified = true
                for (rule in invalidRules) {
                    if (!rule.applyRule(update)) update.swap(rule)
                }
            }
        }
        return invalids.sumOf { it.getMiddle() }.toString()
    }
}