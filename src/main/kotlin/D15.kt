import utils.getIntList

class D15 : Solver {
    override fun solve(lines: Array<String>, part: UInt): String {

        val discs = parseDiscs(lines)
        val result = solve(discs)
        return "$result"
    }

    private fun validateDiscs(discs: Array<Disc>): Boolean {
        for (i in 0..<discs.size) {
            val disc = discs[discs.size - i - 1]
            val pos = (discs.size + disc.currentPosition - i + 1) % disc.totalPositions
            if (pos != 0) {
                return false
            }
        }
        return true
    }

    fun solve(discs: Array<Disc>): Int {
        for (i in 0..Int.MAX_VALUE) {
            if (validateDiscs(discs)) {
                return i + 1
            }
            discs.forEach { it.forward() }
        }
        return -1
    }

    private fun parseDiscs(lines: Array<String>): Array<Disc> {
        return lines
            .filter { it.isNotBlank() }
            .map { getIntList(it) }
            .map { Disc(it[1], it[3]) }
            .toTypedArray()
    }

    class Disc(val totalPositions: Int, val startingPosition: Int) {
        var currentPosition = startingPosition
        fun forward() = currentPosition++
    }
}