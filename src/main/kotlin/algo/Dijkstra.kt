package algo

import ds.GridMap
import java.util.PriorityQueue

typealias Loc = Pair<Int, Int>
typealias DistanceMap = Map<Loc, Int>

class Dijkstra<E>(val gridMap: GridMap<E>) {

    fun completeShortestMap(start: Loc): DistanceMap {
        val q = PriorityQueue<Pair<Loc, Int>>(Comparator.comparing { it.second })
        val toVisit = mutableSetOf<Loc>()
        val d = mutableMapOf<Loc, Int>()
        q.add(start to 0)
        while (q.isNotEmpty()) {
            val v = q.remove()
            d[v.first] = v.second
            val adjacent = gridMap.getAdjacentNodes(v.first)
            for (a in adjacent) {
                if (a !in toVisit) {
                    toVisit.add(a)
                    q.add(a to v.second + 1)
                }
            }
        }
        return d
    }

}