import kotlin.math.max
import kotlin.math.min

class D20 : Solver {
    override fun solve(lines: Array<String>, part: UInt): String {
        val blackList = IPBlackList()

        lines
            .map { it.split("-") }
            .map { it[0].toLong()..it[1].toLong() }
            .forEach { blackList.addRange(it) }

        if (part == 2u) {
            return "${blackList.totalWhitelisted()}"
        }

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

        fun totalWhitelisted(): Long {
            val whitelist = excludeIp(listOf(0L..4294967295L), ranges)
            val merged = merge(whitelist).sortedBy { it.first }
            println("merged $merged")
            var counter = 0L
            for (w in merged) {
                counter += w.count()
            }
            return counter
        }

        private fun merge(whitelist: List<LongRange>): List<LongRange> {
            if (!whitelist.hasOverlaps()) {
                return whitelist
            }
            val newToMerge = whitelist.toMutableList()
            val value = newToMerge.removeFirst()
            val result = mutableListOf<LongRange>()
            var merged = false
            for (n in newToMerge) {
                if (value overlaps n) {
                    merged = true
                    result.add(value merge n)
                } else {
                    result.add(n)
                }
            }
            if(!merged){
                result.add(value)
            }
            return merge(result)
        }

        fun excludeIp(validRanges: List<LongRange>, blacklist: List<LongRange>): List<LongRange> {
            if (blacklist.isEmpty()) {
                return validRanges
            }
            val newBlacklist = blacklist.toMutableList()
            val block = newBlacklist.removeFirst()
            val result = mutableListOf<LongRange>()
            for (r in validRanges) {
                val splitted = r split block
                result.addAll(splitted)
            }
            return excludeIp(result, newBlacklist)
        }


    }
}

infix fun LongRange.iin(r: LongRange): Boolean {
    return r.first in this || r.last in this
}

infix fun LongRange.split(o: LongRange): List<LongRange> {
    if (!(this iin o)) {
        return listOf(this)
    }
    val result = mutableListOf<LongRange>()
    if (o.first > this.first && o.first in this) {
        result.add(this.first..<o.first)
    }
    if (o.last < this.last && o.last in this) {
        result.add(o.last + 1..this.last)
    }
    return result
}

infix fun LongRange.overlaps(o: LongRange): Boolean {
    return o.first in this || o.last in this
}

infix fun LongRange.merge(o: LongRange): LongRange {
    require(this overlaps o) {
        "There is no overlap"
    }
    return min(this.first, o.first)..max(this.last, o.last)
}

fun List<LongRange>.hasOverlaps(): Boolean {
    for (i in 0..<this.size) {
        val ival = this[i]
        for (j in i + 1..<this.size) {
            val jval = this[j]
            if (ival overlaps jval) {
                return true
            }
        }
    }
    return false
}