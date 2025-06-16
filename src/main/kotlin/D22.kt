import utils.Direction
import utils.getIntList
import java.util.PriorityQueue
import kotlin.collections.ArrayDeque
import kotlin.math.abs
import kotlin.streams.toList

val FIRST_NODE_LOC = 0 to 0

class D22 : Solver {
    override fun solve(lines: Array<String>, part: UInt): String {
        var idder = 0
        val nodes = lines.toList().stream()
            .filter { it.isNotEmpty() }
            .skip(2)
            .map { getIntList(it) }
            .map { createDevNode(idder++, it[0] to it[1], it[2], it[3]) }
            .toList()
        if (part == 2u) {
            val grid = createDevNodeGrid(nodes)
            NODE_LOC_TARGET = grid.getDevNode(MAX_LOC.first to 0).id
            val result = bfs(grid)
            return "$result"
        }

        return "${getViablePairs(nodes).size}"
    }

    fun totalDist(it: Pair<DevNodeGrid, Int>): Int {
        return it.first.targetDataLoc.distanceTo(it.first.emptyNodeLoc) +
                it.first.targetDataLoc.distanceTo(FIRST_NODE_LOC)
    }

    fun bfs(grid: DevNodeGrid): Int {
        val q = PriorityQueue<Pair<DevNodeGrid, Int>>(Comparator.comparing { totalDist(it) })
        val visited = mutableSetOf<String>()
        q.add(grid to 0)

        while (q.isNotEmpty()) {
            val n = q.remove()!!
            val egrid = n.first
            if (egrid.targetDataLoc == FIRST_NODE_LOC) {
                return n.second
            }
            val viables = egrid.viableFromEmpty()
            for (v in viables) {
                val newGrid = egrid.move(v.first.loc, v.second)
                if (newGrid == null) {
                    continue
                }
                val newGridHash = newGrid.gridHash()
                if (newGridHash !in visited) {
                    q.add(newGrid to n.second + 1)
                    visited.add(newGridHash)
                }
            }
        }
        throw IllegalStateException("Could not find solution to grid")
    }

    private fun getViablePairs(nodes: List<DevNode>): List<Pair<NodeLoc, NodeLoc>> {
        val result = mutableListOf<Pair<NodeLoc, NodeLoc>>()
        for (i in 0..<nodes.size) {
            val a = nodes[i]
            for (j in i + 1..<nodes.size) {
                val b = nodes[j]
                if (isViableTarget(b)) {
                    result.add(a.loc to b.loc)
                }
                if (isViableTarget(a)) {
                    result.add(b.loc to a.loc)
                }
            }
        }
        return result
    }


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

fun isViableTarget(b: DevNode): Boolean {
    return b.used == 0
}

typealias NodeLoc = Pair<Int, Int>


var NODE_LOC_TARGET = 0

data class DevNode(
    val id: Int,
    val loc: NodeLoc, val size: Int, val used: Int,
    val dataLoc: List<Int> = listOf()
) { // dataLoc will have the positions of the data inside so if you move 0-0 to 1-1, 1-1 will have 1-1,0-0

    fun available(): Int {
        return size - used
    }


    fun addData(a: DevNode): DevNode {
        val listData = this.dataLoc.toMutableList()
        listData.addAll(a.dataLoc)
        return DevNode(id, loc, size, used + a.used, listData)
    }

    fun clear(): DevNode {
        return DevNode(id, loc, size, 0)
    }
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

class DevNodeGrid(
    val parent: DevNodeGrid?,
    val grid: List<List<DevNode>>,
    val targetDataLoc: NodeLoc,
    val emptyNodeLoc: NodeLoc = getEmptyNodeLoc(grid)
) {
    val parentOverwrites = mutableListOf<DevNode>()


    fun move(loc: NodeLoc, dir: Direction): DevNodeGrid? {
        val newLoc = loc.plus(dir.diff())
        var a = getDevNode(loc)
        var b = getDevNode(newLoc)
        if (b.available() < a.used) {
            return null
        }
        if (a.dataLoc.size == 1 && a.dataLoc.first() == NODE_LOC_TARGET && b.dataLoc.isNotEmpty()) {
            // target data will mingle with others, return null
            return null
        }
        b = b.addData(a)
        a = a.clear()
        var newTargetDataLoc = targetDataLoc
        if (b.dataLoc.size == 1 && b.dataLoc.first() == NODE_LOC_TARGET) {
            newTargetDataLoc = b.loc
        }
        val newGrid = DevNodeGrid(this, listOf(), newTargetDataLoc, a.loc)
        newGrid.parentOverwrites.add(a)
        newGrid.parentOverwrites.add(b)
        return newGrid
    }

    fun getDevNode(loc: NodeLoc): DevNode {
        for (node in parentOverwrites) {
            if (node.loc == loc) {
                return node
            }
        }
        if (parent != null) {
            return parent.getDevNode(loc)
        }
        return grid[loc.second][loc.first]
    }


    fun viableFromEmpty(): Collection<Pair<DevNode, Direction>> {
        return utils.CARDINAL_DIRECTIONS
            .map { emptyNodeLoc.plus(it.diff()) to it.inverse() }
            .filter { it.first.isValid(MAX_LOC) }
            .map { getDevNode(it.first) to it.second }
            .toList()
    }

    fun gridHash(): String {
        return "${this.emptyNodeLoc},${this.targetDataLoc}"
    }
}


fun getEmptyNodeLoc(grid: List<List<DevNode>>): NodeLoc {
    for (nodes in grid) {
        for (node in nodes) {
            if (node.dataLoc.isEmpty()) {
                return node.loc
            }
        }
    }
    throw IllegalStateException("No empty node")
}

fun NodeLoc.distanceTo(o: NodeLoc): Int {
    val a = abs(this.first - o.first)
    val b = abs(this.second - o.second)
    return a + b
}


fun createDevNode(id: Int, loc: NodeLoc, size: Int, used: Int): DevNode {
    return DevNode(
        id, loc, size, used, if (used > 0) {
            listOf(id)
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
    MAX_LOC = maxLoc
    val targetDataLoc = MAX_LOC.first to 0
    return DevNodeGrid(null, grid, targetDataLoc)
}

var MAX_LOC = 0 to 0