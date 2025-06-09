import utils.getIntList
import kotlin.streams.toList

class D22 : Solver {
    override fun solve(lines: Array<String>, part: UInt): String {
        val nodes = lines.toList().stream()
            .filter { it.isNotEmpty() }
            .skip(2)
            .map { getIntList(it) }
            .map { DevNode(it[0] to it[1], it[2], it[3]) }
            .toList()

        return "${countViablePairs(nodes)}"
    }

    private fun countViablePairs(nodes: List<DevNode>): Int {
        var counter = 0
        for (i in 0..<nodes.size) {
            val a = nodes[i]
            for (j in i + 1..<nodes.size) {
                val b = nodes[j]
                if (isViable(a, b)) {
                    counter++
                }
                if (isViable(b, a)) {
                    counter++
                }
            }
        }
        return counter
    }

    private fun isViable(a: DevNode, b: DevNode): Boolean {
        if (a.used == 0) {
            return false
        }
        return a.used <= b.available()
    }

}

typealias NodeLoc = Pair<Int, Int>

data class DevNode(val loc: NodeLoc, val size: Int, val used: Int) {

    fun available(): Int {
        return size - used
    }
}