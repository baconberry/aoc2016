import utils.getUIntList

typealias Display<E> = Array<Array<E>>
typealias DisplayInstruction = Pair<String, Pair<Int, Int>>

class D8(val rows: Int, val columns: Int) : Solver {
    override fun solve(lines: Array<String>, part: UInt): String {
        val display = createDisplayBoolean(rows, columns)

        lines
            .filter { it.isNotBlank() }
            .map { it.toDisplayInstruction() }
            .forEach {
                when (it.instruction()) {
                    "rect" -> display.rect(it.a(), it.b(), true)
                    "rr" -> display.rotateRow(it.a(), it.b())
                    "rc" -> display.rotateColumn(it.a(), it.b())
                }
            }
        return "${display.countValue(true)}"
    }
}

fun createDisplayBoolean(rows: Int, columns: Int): Array<Array<Boolean>> {
    val display = Array<Array<Boolean>>(rows, {
        Array<Boolean>(columns, {
            false
        })
    })
    return display
}

fun DisplayInstruction.instruction(): String {
    return this.first
}

fun DisplayInstruction.a(): Int {
    return this.second.first
}

fun DisplayInstruction.b(): Int {
    return this.second.second
}


fun CharSequence.toDisplayInstruction(): DisplayInstruction {
    val numbers = getUIntList(this)
    val np = numbers[0].toInt() to numbers[1].toInt()
    if (this.startsWith("rect")) {
        return "rect" to np
    }
    if (this.contains("row")) {
        return "rr" to np
    }
    return "rc" to np
}

fun <E> Display<E>.rect(x: Int, y: Int, value: E) {
    for (yy in 0..<y) {
        for (xx in 0..<x) {
            this[yy][xx] = value
        }
    }
}

fun <E> Display<E>.rotateRow(row: Int, rotateBy: Int) {
    val copy = this[row].copyOf()
    for (x in 0..<copy.size) {
        this[row][(x + rotateBy) % copy.size] = copy[x]
    }
}

fun <E> Display<E>.rotateColumn(col: Int, rotateBy: Int) {
    val copy = mutableListOf<E>()
    for (y in 0..<this.size) {
        copy.add(this[y][col])
    }
    for (y in 0..<copy.size) {
        this[(y + rotateBy) % this.size][col] = copy[y]
    }
}

fun <E> Display<E>.countValue(value: E): Int {
    return this
        .flatMap { it.toList() }
        .count { it == value }
}
