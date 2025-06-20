import algo.Dijkstra
import algo.DistanceMap
import algo.Loc
import ds.GridMap
import utils.CARDINAL_DIRECTIONS

class D24 : Solver {
    override fun solve(lines: Array<String>, part: UInt): String {
        val gridMap = createCharGridmap(lines.toList())
        val digitLocations = gridMap.getNumericalLocations()
        val digitDistances = mutableMapOf<Int, DistanceMap>()
        val dijkstra = Dijkstra(gridMap)
        for (d in digitLocations.entries) {
            digitDistances[d.key] = dijkstra.completeShortestMap(d.value)
        }
        val result = bfs(digitDistances, digitLocations, listOf(0))
        return "$result"
    }

    fun bfs(
        distances: Map<Int, DistanceMap>,
        locations: Map<Int, Loc>,
        path: List<Int> = listOf(),
        distance: Int = 0
    ): Int {
        if (path.size == distances.size) {
            return distance
        }
        var min = Int.MAX_VALUE

        for (k in distances.keys) {
            if (k in path) {
                continue
            }
            val newPath = path.toMutableList()
            newPath.add(k)
            val localResult = if (newPath.size == 1) {
                bfs(distances, locations, newPath, 0)
            } else {
                val lastK = path.last()
                val kToLastKDistance = distances[lastK]!![locations[k]!!]!!

                bfs(distances, locations, newPath, distance + kToLastKDistance)
            }
            if (localResult < min) {
                min = localResult
            }
        }
        return min
    }

}

class CharGridMap(val charGrid: List<CharArray>) : GridMap<Char> {
    val maxLoc = getMaxLoc(charGrid)

    private fun getMaxLoc(grid: List<CharArray>): Loc {
        return grid[0].size - 1 to grid.size - 1
    }

    override fun getNode(loc: Loc): Char {
        return charGrid[loc.second][loc.first]
    }

    override fun getAdjacentNodes(loc: Loc): List<Loc> {
        val result = mutableListOf<Loc>()
        for (direction in CARDINAL_DIRECTIONS) {
            val newLoc = loc.plus(direction.diff())
            if (newLoc.isValid(maxLoc)) {
                val node = getNode(newLoc)
                if (node != '#') {
                    result.add(newLoc)
                }
            }
        }
        return result
    }

    fun getNumericalLocations(): Map<Int, Loc> {
        val result = mutableMapOf<Int, Loc>()
        for (y in 0..<charGrid.size) {
            val row = charGrid[y]
            for (x in 0..<row.size) {
                if (row[x].isDigit()) {
                    result[row[x].digitToInt()] = x to y
                }
            }
        }
        return result
    }
}

fun createCharGridmap(lines: List<String>): CharGridMap {
    val charGrid = lines
        .filter { it.isNotEmpty() }
        .map { it.toCharArray() }
        .toList()
    return CharGridMap(charGrid)
}