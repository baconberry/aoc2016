import utils.parseDirection

class D2 : Solver {

    private val numpad: Array<Array<Char>> = arrayOf(
        arrayOf('7', '8', '9'),
        arrayOf('4', '5', '6'),
        arrayOf('1', '2', '3'),
    )

    override fun solve(lines: Array<String>, part: UInt): String {

        var (x, y) = 1 to 1
        val code = mutableListOf<Char>()
        for (line in lines) {
            if (line.isBlank()) {
                continue;
            }
            for (c in line.chars()) {
                val d = parseDirection(c.toChar())
                val (dx, dy) = d.diff()
                val nx = x + dx
                val ny = y + dy
                if (nx < 0 || nx > 2 || ny < 0 || ny > 2) {
                    continue
                }
                x = nx
                y = ny
            }
            code.add(numpad[y][x])
        }
        return code.joinToString("")
    }
}