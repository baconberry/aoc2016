import utils.getUIntList

typealias PUUU = Pair<Pair<UInt, UInt>, UInt>

class D3 : Solver {
    override fun solve(lines: Array<String>, part: UInt): String {
        if (part == 2u) {
            val a = mutableListOf<UInt>()
            val b = mutableListOf<UInt>()
            val c = mutableListOf<UInt>()
            lines
                .filter { it.isNotBlank() }
                .map { getUIntList(it) }
                .forEach {
                    a.add(it[0])
                    b.add(it[1])
                    c.add(it[2])
                }
            val result = countTriangles(a) + countTriangles(b) + countTriangles(c)
            return "$result"
        }

        val result = lines
            .filter { it.isNotBlank() }
            .map { getUIntList(it) }
            .map { isValidTriangle(it) }
            .count { it }

        return "$result"
    }

    private fun countTriangles(a: List<UInt>): UInt {
        var offset = 0
        var counter = 0u
        while (offset < a.size) {
            if (isValidTriangle(a.subList(offset, offset + 3))) {
                counter++
            }
            offset += 3
        }
        return counter
    }

    private fun isValidTriangle(l: List<UInt>): Boolean {
        require(l.size == 3) {
            "Number list must be of size 3"
        }
        val a = l[0]
        val b = l[1]
        val c = l[2]
        return a + b > c && a + c > b && b + c > a
    }

    fun puuuToList(p: PUUU): List<UInt> {
        val (z, c) = p
        val (a, b) = z
        return listOf(a, b, c)
    }
}
