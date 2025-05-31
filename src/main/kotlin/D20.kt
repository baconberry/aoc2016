class D20 : Solver {
    override fun solve(lines: Array<String>, part: UInt): String {
        val blackList = IPBlackList()
        lines
            .map { it.split("-") }
            .map { it[0].toLong()..it[1].toLong() }
            .forEach { blackList.addRange(it) }
        return "${blackList.lowestAllowedNaive()}"
    }

    class IPBlackList() {
        val ranges = mutableListOf<LongRange>()
        fun addRange(range: LongRange) {
            ranges.add(range)
        }

        fun lowestAllowedNaive(): Long {
            ranges.sortBy { it.first }
            anotheri@ for (i in 0L..Long.MAX_VALUE) {
                for (r in ranges) {
                    if (i in r) {
                        continue@anotheri
                    }
                }
                return i
            }
            return -1
        }
    }
}