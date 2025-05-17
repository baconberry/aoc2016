import utils.Direction
import java.security.MessageDigest

typealias Position = Pair<Int, Int>

class D17 : Solver {
    override fun solve(lines: Array<String>, part: UInt): String {
        val passcode = lines.first()
        val maze = MD5Maze(4, 4, passcode)
        if (part == 2u) {
            val result = maze.maxPath(0 to 0)
            val max = result!!.length - passcode.length
            return "$max"
        }
        return "${maze.minPath(0 to 0)}"
    }

    class MD5Maze(val width: Int, val height: Int, val passcode: String) {
        var minPathLength = Int.MAX_VALUE
        var maxPathLength = Int.MIN_VALUE
        val validRange = 0..3

        fun maxPath(pos: Position, path: String = passcode): String? {
            if (pos == width - 1 to height - 1) {
                return path
            }
            val openedDoors = path.md5OpenDoors()
            var max: String? = null
            for (direction in openedDoors) {
                val newPos = pos.plus(direction.diff().invertSecond())
                if (newPos.first !in validRange || newPos.second !in validRange) {
                    continue
                }
                val localRes = maxPath(newPos, "$path${direction.toCommonDirName()}")
                if (localRes == null) {
                    continue
                }
                if (localRes.length > maxPathLength) {
                    maxPathLength = localRes.length
                }
                if (max == null || localRes.length > max.length) {
                    max = localRes
                }
            }
            return max
        }

        fun minPath(pos: Position, path: String = passcode): String? {
            if (path.length > minPathLength) {
                return null
            }
            if (pos == width - 1 to height - 1) {
                return path
            }
            val openedDoors = path.md5OpenDoors()
            var min: String? = null
            for (direction in openedDoors) {
                val newPos = pos.plus(direction.diff().invertSecond())
                if (newPos.first !in validRange || newPos.second !in validRange) {
                    continue
                }
                val localRes = minPath(newPos, "$path${direction.toCommonDirName()}")
                if (localRes == null) {
                    continue
                }
                if (localRes.length < minPathLength) {
                    minPathLength = localRes.length
                }
                if (min == null ||
                    localRes.length < min.length
                ) {
                    min = localRes
                }
            }
            return min
        }
    }
}

fun Position.invertSecond(): Position {
    return this.first to -this.second
}

val md5md: MessageDigest = MessageDigest.getInstance("MD5")

fun String.md5(): String {
    return this.digest(md5md).lowercase()
}

val openValues = setOf<Char>('b', 'c', 'd', 'e', 'f')
fun String.md5OpenDoors(): List<Direction> {
    val md5 = this.md5()
    val result = mutableListOf<Direction>()
    val md5Pos = listOf<Pair<Int, Direction>>(
        0 to Direction.N,
        1 to Direction.S,
        2 to Direction.W,
        3 to Direction.E,
    )
    for (p in md5Pos) {
        if (md5[p.first] in openValues) {
            result.add(p.second)
        }
    }
    return result
}

