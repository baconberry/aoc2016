import utils.getOneUInt
import kotlin.math.min

class D4 : Solver {
    override fun solve(lines: Array<String>, part: UInt): String {
        val result = lines
            .filter { it.isNotBlank() }
            .map { it.split("-") }
            .filter { isRealRoom(it) }
            .sumOf { getSectorId(it) }

        return "$result"
    }

    private fun getSectorId(it: List<String>): UInt {
        return getOneUInt(it.last())
    }

    private fun isRealRoom(it: List<String>): Boolean {
        val last = it.last()
        val checksum = last.subSequence(last.indexOf('[') + 1, last.indexOf(']'))
        val frequencyMap = mutableMapOf<Char, UInt>()
        it.subList(0, it.size - 1)
            .forEach { countChars(it, frequencyMap) }
        var freqList = frequencyMap.entries.toList()
        freqList = freqList.sortedWith(compareBy({ it.value }, { -it.key.code }))
        freqList = freqList.reversed()

        for (i in 0..<min(freqList.size, checksum.length)) {
            if (checksum[i] != freqList[i].key) {
                return false;
            }
        }
        return true
    }

    private fun countChars(it: String, frequencyMap: MutableMap<Char, UInt>) {
        for (c in it.chars()) {
            val char = c.toChar()
            var count = frequencyMap[char]
            if (count == null) {
                count = 0u
            }
            frequencyMap[char] = count + 1u
        }
    }

}