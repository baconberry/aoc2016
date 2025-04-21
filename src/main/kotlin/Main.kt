fun main(args: Array<String>) {
    val problem = args[0].toInt()
    val part = args[1].toUInt()
    val lines = readLines()

    println("Solving problem $problem part $part lines size ${lines.size}")

    val solver: Solver = when (problem) {
        1 -> D1()
        2 -> D2()
        3 -> D3()
        4 -> D4()
        5 -> D5()
        6 -> D6()
        else -> {
            throw IllegalArgumentException("No problem with index $problem")
        }
    }
    val result = solver.solve(lines, part)

    println("Result [$result]")
}

fun readLines(): Array<String> {
    val result = mutableListOf<String>()
    while (true) {
        val line = readlnOrNull() ?: break
        result.add(line)
    }
    return result.toTypedArray()
}
