import java.util.PriorityQueue
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
            return "${blackList.totalMinusBlacklist()}"
        }

        return "${blackList.lowestAllowedNaive()}"
    }

    class IPBlackList() {
        val ranges = mutableListOf<LongRange>()
        fun addRange(range: LongRange) {
            ranges.add(range)
        }

        fun totalMinusBlacklist(): Int {
            val total = 4294967295
            val rangesSorted = ranges.sortedBy { it.first }
            val localBL = merge(rangesSorted).sortedBy { it.first }
            val q = PriorityQueue<LongRange>(Comparator.comparing { it.first })
            q.addAll(localBL)
            var prev = q.remove()
            var totalWhitelisted = prev.first
            while (q.isNotEmpty()) {
                val range = q.remove()
                totalWhitelisted += range.first - 1 - prev.last
                prev = range
            }
            totalWhitelisted += total - prev.last
            return totalWhitelisted.toInt()
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

        private fun merge(whitelist: List<LongRange>): List<LongRange> {
            if (!whitelist.hasOverlaps()) {
                return whitelist
            }
            val newToMerge = whitelist.toMutableList()
            val value = newToMerge.removeFirst()
            val result = mutableListOf<LongRange>()
            var merged = false
            for (n in newToMerge) {
                if (value overlaps n || value.last + 1 == n.first) {
                    merged = true
                    result.add(value merge n)
                } else {
                    result.add(n)
                }
            }
            if (!merged) {
                result.add(value)
            }
            return merge(result)
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
    require(this overlaps o || this.last + 1 == o.first) {
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