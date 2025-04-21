import utils.Direction
import utils.getOneUInt
import utils.parseDirection
import kotlin.math.abs

typealias PDU = Pair<Direction, UInt>

class D1 : Solver {
    override fun solve(lines: Array<String>, part: UInt): String {
        val instructions = parseLines(lines)

        val (x, y) = if (part == 1u) {
            walk(instructions)
        } else {
            walkAndGetFirstRepeated(instructions)
        }
        val result = abs(x) + abs(y)

        return "$result"
    }

    private fun walkAndGetFirstRepeated(instructions: List<PDU>): Pair<Int, Int> {
        var currentPoint = 0 to 0
        val visited = mutableSetOf(currentPoint)
        var currentDirection = Direction.NONE
        for ((d, l) in instructions) {
            currentDirection = currentDirection.changeTo(d)
            val res = currentDirection.plusIncrement(l.toInt(), 1)
            for ((x, y) in res) {
                currentPoint = currentPoint.first + x to currentPoint.second + y
                if (currentPoint in visited) {
                    return currentPoint
                } else {
                    visited.add(currentPoint)
                }
            }
        }
        return currentPoint
    }

    private fun walk(instructions: List<PDU>): Pair<Int, Int> {
        var currentPoint = 0 to 0
        var currentDirection = Direction.NONE
        for ((d, l) in instructions) {
            currentDirection = currentDirection.changeTo(d)
            currentPoint = currentDirection.plus(currentPoint, l.toInt())
        }
        return currentPoint
    }

    private fun parseLines(lines: Array<String>): List<PDU> {
        val result = mutableListOf<PDU>()
        for (line in lines) {
            if (line.isEmpty()) {
                break
            }
            for (s in line.split(",")) {
                val d = parseDirection(s.trim()[0])
                val n = getOneUInt(s)
                if (n != UInt.MAX_VALUE) {
                    result.add(d to n)
                }
            }
        }

        return result
    }

}
