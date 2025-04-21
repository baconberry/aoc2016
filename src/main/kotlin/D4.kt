import utils.getOneUInt
import kotlin.math.min

class D4 : Solver {
    override fun solve(lines: Array<String>, part: UInt): String {
        if (part == 2u) {
            lines
                .filter { it.isNotBlank() }
                .map { it.split("-") }
                .filter { isRealRoom(it) }
                .map { decryptRoom(it) }
                .forEach { println(it) }

            return ""
        }
        val result = lines
            .filter { it.isNotBlank() }
            .map { it.split("-") }
            .filter { isRealRoom(it) }
            .sumOf { getSectorId(it) }

        return "$result"
    }

    private fun decryptRoom(it: List<String>): String {
        val a = 'a'.code
        val offset = 'z' - 'a' + 1
        val sectorId = getOneUInt(it.last())
        val forward = sectorId % offset.toUInt()
        val sb = StringBuilder()
        for (s in it.subList(0, it.size - 1)) {
            for (c in s.chars()) {
                val nc = (c + forward.toInt() - a) % offset
                sb.append((a + nc).toChar())
            }
            sb.append(" ")
        }
        sb.append(it.last())
        return sb.toString()
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