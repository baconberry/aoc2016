import utils.parseDirection

class D2 : Solver {

    private val numpad: Array<Array<Char>> = arrayOf(
        arrayOf('7', '8', '9'),
        arrayOf('4', '5', '6'),
        arrayOf('1', '2', '3'),
    )

    private val prettyNumpad: Array<Array<Char>> = arrayOf(
        arrayOf('_', '_', 'D', '_', '_'),
        arrayOf('_', 'A', 'B', 'C', '_'),
        arrayOf('5', '6', '7', '8', '9'),
        arrayOf('_', '2', '3', '4', '_'),
        arrayOf('_', '_', '1', '_', '_'),
    )


    override fun solve(lines: Array<String>, part: UInt): String {
        var npad = numpad
        var startPos = 1 to 1
        if (part == 2u) {
            npad = prettyNumpad
            startPos = 0 to 2
        }
        val result = walkNumpad(lines, npad, startPos)
        return result.joinToString("")
    }

    private fun walkNumpad(lines: Array<String>, npad: Array<Array<Char>>, startPos: PII): List<Char> {
        var loc = startPos
        val code = mutableListOf<Char>()
        for (line in lines) {
            if (line.isBlank()) {
                continue;
            }
            for (c in line.chars()) {
                val d = parseDirection(c.toChar())
                val n = loc plus d.diff()
                val (x, y) = n
                if (x < 0 || y < 0 || y >= npad.size) {
                    continue
                }
                val row = npad[y]
                if (x >= row.size || npad[y][x] == '_') {
                    continue
                }
                loc = n
            }
            code.add(npad[loc.second][loc.first])
        }
        return code
    }
}

typealias PII = Pair<Int, Int>

infix fun PII.plus(a: PII): PII {
    val (x, y) = a
    return this.first + x to this.second + y
}