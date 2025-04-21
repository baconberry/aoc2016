package grid

typealias Grid<E> = Array<Array<E>>

fun parseCharGrid(lines: Array<String>): Grid<Char> {
    val x = lines.first().length
    val y = lines.count { it.isNotBlank() }
    val arr = Array(y) { Array(x) { ' ' } }
    for (i in 0..<y) {
        val line = lines[i]
        for (j in 0..<x) {
            arr[i][j] = line[j]
        }
    }
    return arr
}

inline fun <reified E> Grid<E>.rotate(): Grid<E> {
    val y = this.first().size
    val x = this.size
    val tArr = mutableListOf<Array<E>>()
    for (i in 0..<y) {
        val list = mutableListOf<E>()
        for (j in 0..<x) {
            list.add(this[j][i])
        }
        tArr.add(list.toTypedArray())
    }
    return tArr.toTypedArray()
}