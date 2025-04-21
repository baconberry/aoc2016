import utils.getUIntList

class D3 : Solver {
    override fun solve(lines: Array<String>, part: UInt): String {

        val result = lines
            .filter { it.isNotBlank() }
            .map { getUIntList(it) }
            .map { isValidTriangle(it) }
            .count { it }

        return "$result"
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
}
