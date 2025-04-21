import utils.Direction
import utils.getOneUInt
import utils.parseDirection
import kotlin.math.abs

typealias PDU = Pair<Direction, UInt>

class D1 : Solver {
    override fun solve(lines: Array<String>, part: UInt): String {
        val instructions = parseLines(lines)

        val (x, y) = walk(instructions)
        val result = abs(x + y)

        return "$result"
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
