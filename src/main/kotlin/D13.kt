import utils.cardinalDirections
import java.util.PriorityQueue

typealias Wall = Boolean
typealias Coord = Pair<Int, Int>
typealias Path = List<Coord>
typealias CoordDist = Pair<Coord, Int>

class D13 : Solver {
    override fun solve(lines: Array<String>, part: UInt): String {

        val favNumber = lines.first().toInt()
        val lazyMap = LazyBinaryMap(favNumber)

        val result =
            if (part == 1u) {
                lazyMap.shortestPath(1 to 1, 31 to 39)
            } else {
                lazyMap.reachability(1 to 1, 50).size
            }

        return "$result"
    }


    class LazyBinaryMap(val favNumber: Int) {
        val map = mutableMapOf<Coord, Wall>()


        fun shortestPath(start: Coord, end: Coord): Int {
            val visited = mutableSetOf<Coord>()
            val distances = mutableMapOf<Coord, Int>()
            val q = PriorityQueue<CoordDist>(Comparator.comparing { it.second })

            visited.add(start)
            distances[start] = 0
            getAdjacentNodes(start).forEach { q.add(it to 0) }
            while (q.isNotEmpty()) {
                val (node, prevDistance) = q.poll()
                val dist = prevDistance + 1
                visited.add(node)
                distances[node] = dist
                getAdjacentNodes(node)
                    .filter { !visited.contains(it) }
                    .forEach { q.add(it to dist) }
                if (node == end) {
                    return dist
                }
            }
            return Int.MAX_VALUE
        }

        fun reachability(start: Coord, maxDist: Int): Set<Coord> {
            val visited = mutableSetOf<Coord>()
            val distances = mutableMapOf<Coord, Int>()
            val q = PriorityQueue<CoordDist>(Comparator.comparing { it.second })

            visited.add(start)
            distances[start] = 0
            getAdjacentNodes(start).forEach { q.add(it to 0) }
            while (q.isNotEmpty()) {
                val (node, prevDistance) = q.poll()
                val dist = prevDistance + 1
                visited.add(node)
                distances[node] = dist
                getAdjacentNodes(node)
                    .filter { !visited.contains(it) }
                    .filter { !it.isNegative() }
                    .filter { dist < maxDist }
                    .forEach { q.add(it to dist) }
            }
            return visited
        }

        fun getAdjacentNodes(pos: Coord): List<Coord> {
            val result = mutableListOf<Coord>()
            for (d in cardinalDirections()) {
                val dp = d.diff().plus(pos)
                if (!isWall(dp)) {
                    result.add(dp)
                }
            }
            return result
        }

        fun isWall(coord: Coord): Wall {
            val possiblyWall = map[coord]
            if (possiblyWall != null) {
                return possiblyWall
            }
            val ones = secondaryFn(coord.first, coord.second)
                .toString(2)
                .toFrequencyMap()
                .getOrDefault('1', 0)

            val isWall = ones % 2 == 1
            map[coord] = isWall

            return isWall
        }

        fun primaryFn(x: Int, y: Int): Long {
            return ((x * x) + (3 * x) + (2 * x * y) + y + (y * y)).toLong()
        }

        fun secondaryFn(x: Int, y: Int): Long {
            return primaryFn(x, y) + favNumber
        }

    }
}

fun CharSequence.toFrequencyMap(): Map<Char, Int> {
    val map = mutableMapOf<Char, Int>()

    for (c in this.chars()) {
        val count = map.getOrDefault(c.toChar(), 0)
        map[c.toChar()] = count + 1
    }

    return map
}

fun Coord.isNegative(): Boolean {
    return this.first < 0 || this.second < 0
}