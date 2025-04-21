import grid.parseCharGrid
import grid.rotate

class D6 : Solver {
    override fun solve(lines: Array<String>, part: UInt): String {
        val grid = parseCharGrid(lines).rotate()
        val word = StringBuilder()
        for (chars in grid) {
            word.append(mostCommonChar(chars))
        }
        return word.toString()
    }

    private fun mostCommonChar(chars: Array<Char>): Char {
        return freqMap(chars).entries
            .maxBy { it.value }
            .key
    }
}

fun freqMap(chars: Array<Char>): Map<Char, Int> {
    val map = mutableMapOf<Char, Int>()
    for (char in chars) {
        val count = map.getOrDefault(char, 0)
        map[char] = count + 1
    }
    return map
}