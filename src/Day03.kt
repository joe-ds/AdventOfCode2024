class Day03: Day(3) {
    private val input = this.getInput()

    private fun parseAndMul(instr: String): Int {
        val matches = Regex("\\d{1,3}").findAll(instr)
            .map { it.groupValues }.flatten().map { it.toInt() }
        return matches.first() * matches.last()
    }

    private fun findMuls(s: String): List<String> = Regex("mul\\(\\d{1,3},\\d{1,3}\\)").findAll(s)
        .map { it.groupValues }.toList().flatten()

    override fun partOne(): String {
        val answer = findMuls(input)
            .map(::parseAndMul).sum()
        return answer.toString()
    }

    override fun partTwo(): String {
        // This looks confusing, so let me explain.
        // We know that don't()s split things so that anything after a don't()
        // is disabled. And we know that do()s reenable them. So what we do is
        // split the input on don't(), and then run the same function from part
        // one on the first bit, since it starts off activated. Then we take
        // the rest, and split on do()s, and apply the same function from part
        // one on all the pieces except the first.
        // ...
        // Yeah, I'm sure that's cleared it all up.

        val donts = input.split("don't()")
        val start = findMuls(donts.first()).map(::parseAndMul).sum()
        val rest = donts.asSequence().drop(1).map {
            it.split("do()")
        }.filter { it.size > 1 }.map { findingdos -> findingdos.drop(1).map(::findMuls).map { it.map(::parseAndMul) } }
            .flatten().flatten().sum()
        return (start + rest).toString()
    }
}