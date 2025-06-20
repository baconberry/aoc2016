package ds

import algo.Loc

interface GridMap<E> {

    fun getNode(loc: Loc): E
    fun getAdjacentNodes(loc: Pair<Int, Int>): List<Loc>

}