import utils.Direction
import utils.getIntList
import java.security.MessageDigest
import kotlin.math.abs
import kotlin.streams.toList

class D22 : Solver {
    override fun solve(lines: Array<String>, part: UInt): String {
        val nodes = lines.toList().stream()
            .filter { it.isNotEmpty() }
            .skip(2)
            .map { getIntList(it) }
            .map { createDevNode(it[0] to it[1], it[2], it[3]) }
            .toList()
        if (part == 2u) {
            val grid = createDevNodeGrid(nodes)
            NODE_LOC_TARGET = grid.maxLoc.first to 0
            val result = solveGrid(grid, 0, mutableSetOf<String>())
            return "$result"
        }

        return "${getViablePairs(nodes).size}"
    }

    fun solveGrid(
        grid: DevNodeGrid,
        steps: Int = 0,
        visitedPaths: MutableSet<String>,
        path: List<Pair<NodeLoc, Direction>> = listOf()
    ): Int {
        val first = grid.getDevNode(0 to 0)
        if (first.dataLoc.size == 1 && first.dataLoc.first() == grid.maxLoc.second to 0) {
            return steps
        }

        if (path.size > 10000) {
//            println("max path size exceeded")
            return Int.MAX_VALUE
        }
        val pathHash = grid.gridHash()
        if (pathHash in visitedPaths) {
//            println("path visited, exiting")
            return Int.MAX_VALUE
        }
        visitedPaths.add(pathHash)

        val viable = grid.viablePairs()
        var min = Int.MAX_VALUE
        for (v in viable) {
//            if (isReturning(path, v)) {
////                println("returning... exiting")
//                continue
//            }
            val newPath = path.toMutableList()
//            if (hasCycleIterative(newPath)) {
////                println("cycle detected... exiting")
//                continue
//            }
            newPath.add(v.first.loc to v.second)
            val localResult = solveGrid(grid.move(v.first.loc, v.second)!!, steps + 1, visitedPaths, newPath)
            if (localResult < min) {
                println("found new min localResult $localResult")
                return localResult
                min = localResult
            }
        }
        return min
    }


    private fun isReturning(path: List<Pair<NodeLoc, Direction>>, v: Pair<DevNode, Direction>): Boolean {
        if (path.isEmpty()) {
            return false
        }
        val last = path.last()
        return last.first.plus(last.second.diff()) == v.first.loc &&
                v.first.loc.plus(v.second.diff()) == last.first
    }

    private fun getViablePairs(nodes: List<DevNode>): List<Pair<NodeLoc, NodeLoc>> {
        val result = mutableListOf<Pair<NodeLoc, NodeLoc>>()
        for (i in 0..<nodes.size) {
            val a = nodes[i]
            for (j in i + 1..<nodes.size) {
                val b = nodes[j]
                if (isViable(a, b)) {
                    result.add(a.loc to b.loc)
                }
                if (isViable(b, a)) {
                    result.add(b.loc to a.loc)
                }
            }
        }
        return result
    }


}

val sha256MD = MessageDigest.getInstance("SHA-256")
fun String.sha256Hex(): String {
    return this.digest(sha256MD)
}

fun hashPath(path: List<Pair<NodeLoc, Direction>>): String {
    val sb = StringBuilder()
    for (pair in path) {
        sb.append("${pair.first}:${pair.second},")
    }
    return sb.toString().sha256Hex()
}

fun <E> hasCycle(path: List<E>, lastPath: List<E> = listOf()): Boolean {
    if (path.isEmpty() || lastPath.size > path.size) {
        return false
    }
    val newPath = ArrayDeque(lastPath)
    newPath.addFirst(path.last())
    val pathWithoutLast = path.subList(0, path.size - 1)

    if (newPath.size <= pathWithoutLast.size && pathWithoutLast.subList(
            pathWithoutLast.size - newPath.size,
            pathWithoutLast.size
        ) == newPath
    ) {
        return true
    }
    return hasCycle(pathWithoutLast, newPath)
}

fun <E> hasCycleIterative(path: List<E>): Boolean {
    if (path.isEmpty()) {
        return false
    }
    val a = ArrayDeque(path)
    val b = ArrayDeque<E>()
    while (a.isNotEmpty() && b.size <= a.size) {
        b.addFirst(a.removeLast())
        if (b.size <= a.size && a.subList(a.size - b.size, a.size) == b) {
            return true
        }
    }
    return false
}

fun isViable(a: DevNode, b: DevNode): Boolean {
    if (a.used == 0) {
        return false
    }
    return a.used <= b.available()
}

typealias NodeLoc = Pair<Int, Int>


var NODE_LOC_TARGET = 0 to 0
data class DevNode(
    val loc: NodeLoc, val size: Int, val used: Int,
    val dataLoc: List<NodeLoc> = listOf()
) { // dataLoc will have the positions of the data inside so if you move 0-0 to 1-1, 1-1 will have 1-1,0-0

    fun available(): Int {
        return size - used
    }


    fun addData(a: DevNode): DevNode {
        val listData = this.dataLoc.toMutableList()
        listData.addAll(a.dataLoc)
        return DevNode(loc, size, used + a.used, listData)
    }

    fun clear(): DevNode {
        return DevNode(loc, size, 0)
    }
}

fun getMaxLoc(grid: List<List<DevNode>>): Pair<Int, Int> {
    return getMaxLocList(
        grid.stream()
            .flatMap { it.stream() }
            .toList())
}

fun getMaxLocList(nodes: List<DevNode>): NodeLoc {
    var maxX = 0
    var maxY = 0
    nodes.forEach {
        if (it.loc.first > maxX) {
            maxX = it.loc.first
        }
        if (it.loc.second > maxY) {
            maxY = it.loc.first
        }
    }
    return maxX to maxY
}

class DevNodeGrid(val grid: List<List<DevNode>>, val maxLoc: NodeLoc = getMaxLoc(grid)) {


    fun move(loc: NodeLoc, dir: Direction): DevNodeGrid? {
        val newLoc = loc.plus(dir.diff())
        var a = getDevNode(loc)
        var b = getDevNode(newLoc)
        if (b.available() < a.used) {
            return null
        }
        val newGrid = cloneGrid(grid)
        b = b.addData(a)
        a = a.clear()
        newGrid[loc.second][loc.first] = a
        newGrid[newLoc.second][newLoc.first] = b
        return DevNodeGrid(newGrid, maxLoc)
    }

    fun getDevNode(loc: NodeLoc): DevNode {
        return grid[loc.second][loc.first]
    }

    private fun cloneGrid(grid: List<List<DevNode>>): List<MutableList<DevNode>> {
        val g = mutableListOf<MutableList<DevNode>>()
        for (nl in grid) {
            g.add(nl.toMutableList())
        }
        return g
    }

    fun viablePairs(): Collection<Pair<DevNode, Direction>> {
//        val result = ArrayDeque<Pair<DevNode, Direction>>()
        val result = mutableListOf<Pair<DevNode, Direction>>()
//        var count = 0;
        for (row in grid) {
            for (n in row) {
                for (dir in utils.CARDINAL_DIRECTIONS) {
                    val newLoc = n.loc.plus(dir.diff())
                    if (newLoc.isValid(maxLoc)) {
                        val b = getDevNode(newLoc)
                        if (isViable(n, b)) {
                            result.add(n to dir)
//                            if(count++%2==0){
//                                result.addFirst(n to dir)
//                            }else {
//                                result.addLast(n to dir)
//                            }
                        }
                    }
                }
            }
        }
        return result.sortedBy { distanceToTarget(it.first) }
    }

    private fun distanceToTarget(node: DevNode): Int {
        if(node.dataLoc.size == 1 && node.dataLoc.first() == NODE_LOC_TARGET){
            return -1
        }
        if(node.dataLoc.size == 1){
            return node.dataLoc.first().distanceTo(NODE_LOC_TARGET)
        }

        return Int.MAX_VALUE
    }

    fun gridHash(): String {
        val sb = StringBuilder()
        for (row in grid) {
            for (node in row) {
                val values = node.dataLoc.joinToString(".", "[", "]")
                sb.append(values)
            }
        }
        return sb.toString().sha256Hex()
    }

}

fun  NodeLoc.distanceTo(o: NodeLoc): Int {
    val a = abs(this.first-o.first)
    val b = abs(this.second-o.second)
    return a+b
}


fun createDevNode(loc: NodeLoc, size: Int, used: Int): DevNode {
    return DevNode(
        loc, size, used, if (used > 0) {
            listOf(loc)
        } else {
            listOf()
        }
    )
}

fun NodeLoc.isValid(maxLoc: NodeLoc): Boolean {
    if (this.first < 0 || this.first > maxLoc.first) {
        return false
    }
    if (this.second < 0 || this.second > maxLoc.second) {
        return false
    }
    return true
}

fun createDevNodeGrid(nodes: List<DevNode>): DevNodeGrid {
    val grid = mutableListOf<List<DevNode>>()
    val maxLoc = getMaxLocList(nodes)
    for (rowIdx in 0..maxLoc.second) {
        val row = mutableListOf<DevNode>()
        for (colIdx in 0..maxLoc.first) {
            val loc = colIdx to rowIdx
            for (n in nodes) {
                if (n.loc == loc) {
                    row.add(n)
                    break
                }
            }
        }
        grid.add(row)
    }
    return DevNodeGrid(grid, maxLoc)
}