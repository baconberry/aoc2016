import utils.getIntList
import utils.getUIntList
import utils.toPair

class D9 : Solver {
    override fun solve(lines: Array<String>, part: UInt): String {
        val result = if (part == 1u) {
            lines
                .filter { it.isNotBlank() }
                .map { it.trim().decompressMarker() }
                .sumOf { it.length }
        } else {
            lines.filter { it.isNotBlank() }
                .sumOf { it.multiMarkerLength() }
        }
        return "$result"
    }
}

fun CharSequence.withoutFirst(): CharSequence {
    return this.subSequence(1..<this.length)
}

fun CharSequence.subFrom(start: Int): CharSequence {
    return this.subSequence(start..<this.length)
}

fun CharSequence.subFromFor(start: Int, len: Int): CharSequence {
    return this.subSequence(start..<start + len)
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

fun CharSequence.multiMarkerLength(): Long {
    if (this.isEmpty()) {
        return 0
    }
    if (this[0] != '(') {
        return 1 + this.withoutFirst().multiMarkerLength()
    }
    val textStart = this.indexOf(')')
    val marker = this.subSequence(1..<textStart)
    val markerValues = getIntList(marker).toPair()
    val firstMarkerLength = this.subFromFor(textStart + 1, markerValues.first).multiMarkerLength()
    val firstTotal = (markerValues.second) * firstMarkerLength
    return firstTotal + this.subFrom(textStart + 1 + markerValues.first).multiMarkerLength()


}