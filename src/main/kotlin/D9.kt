import utils.getUIntList

class D9 : Solver {
    override fun solve(lines: Array<String>, part: UInt): String {
        val result = lines
            .filter { it.isNotBlank() }
            .map { it.trim().decompressMarker() }
            .sumOf { it.length }
        return "$result"
    }
}

fun CharSequence.decompressMarker(): String {
    if (this.isEmpty()) {
        return ""
    }
    if (this[0] != '(') {
        return "${this[0]}${this.subSequence(1..<this.length).decompressMarker()}"
    }
    val textStart = this.indexOf(')')
    val marker = this.subSequence(1..<textStart)
    val markerValues = getUIntList(marker)
    val normalTextStart = textStart + 1 + markerValues[0].toInt()
    val textToRepeat = this.subSequence(textStart + 1..<normalTextStart)
    val repeatedText = textToRepeat.repeat(markerValues[1].toInt())
    return "${repeatedText}${this.subSequence(normalTextStart..<this.length).decompressMarker()}"
}