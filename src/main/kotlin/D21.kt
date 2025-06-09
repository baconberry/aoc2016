import utils.getIntList
import kotlin.math.max
import kotlin.math.min

class D21 : Solver {
    var reversed = false

    override fun solve(lines: Array<String>, part: UInt): String {
        if (part == 2u) {
            reversed = true
            return solve("fbgdceah", lines.reversed().toTypedArray())
        }
        return solve("abcdefgh", lines)
    }

    fun solve(initialState: String, lines: Array<String>): String {
        var state = initialState
        for (line in lines) {
            state = processLine(line, state)
        }
        return state
    }

    fun processLine(line: String, state: String): String {
        if (reversed) {
            return reversedLine(line, state)
        }
        val arr = getIntList(line)
        return when {
            line.startsWith("swap position") -> state.swapXY(arr[0], arr[1])
            line.startsWith("swap letter") -> state.swapLetters(line[12], line[line.length - 1])
            line.startsWith("rotate left") -> state.rotateLeft(arr[0])
            line.startsWith("rotate right") -> state.rotateRight(arr[0])
            line.startsWith("rotate based on position") -> state.rotateXBased(line[line.length - 1])
            line.startsWith("reverse positions") -> state.reverseSubsequence(arr[0], arr[1])
            line.startsWith("move position") -> state.move(arr[0], arr[1])
            else -> throw IllegalArgumentException("Command $line is not valid")
        }
    }

    private fun reversedLine(line: String, state: String): String {
        val arr = getIntList(line)
        return when {
            line.startsWith("swap position") -> state.swapXY(arr[1], arr[0])
            line.startsWith("swap letter") -> state.swapLetters(line[line.length - 1], line[12])
            line.startsWith("rotate left") -> state.rotateRight(arr[0])
            line.startsWith("rotate right") -> state.rotateLeft(arr[0])
            line.startsWith("rotate based on position") -> state.rotateLeftXBased(line[line.length - 1])
            line.startsWith("reverse positions") -> state.reverseSubsequence(arr[0], arr[1])
            line.startsWith("move position") -> state.move(arr[1], arr[0])
            else -> throw IllegalArgumentException("Command $line is not valid")
        }
    }

}

fun String.swapXY(x: Int, y: Int): String {
    val min = min(x, y)
    val max = max(x, y)
    val minV = this[min]
    val maxV = this[max]
    return "${this.subSequence(0..<min)}$maxV${this.subSequence(min + 1..<max)}$minV${this.subSequence(max + 1..<this.length)}"
}

fun String.swapLetters(a: Char, b: Char): String {
    val sb = StringBuilder()
    for (c in this) {
        when (c) {
            a -> sb.append(b)
            b -> sb.append(a)
            else -> sb.append(c)
        }
    }
    return sb.toString()
}

fun String.rotateRight(n: Int): String {
    val rotateTotal = n % this.length
    if (rotateTotal == 0) {
        return this
    }
    val rotateStart = this.length - rotateTotal
    return "${this.subSequence(rotateStart..<this.length)}${this.subSequence(0..<rotateStart)}"
}

fun String.rotateLeft(n: Int): String {
    return rotateRight(this.length - n)
}

fun String.rotateLeftXBased(x: Char): String {
    var s = this.rotateLeft(1)
    while (s.rotateXBased(x) != this) {
        s = s.rotateLeft(1)
    }
    return s
}

fun String.rotateXBased(x: Char): String {
    val xIdx = this.indexOf(x)
    val totalRotations = if (xIdx >= 4) {
        xIdx + 2
    } else {
        xIdx + 1
    }
    return this.rotateRight(totalRotations)
}

fun String.reverseSubsequence(x: Int, y: Int): String {
    return "${this.subSequence(0..<x)}${this.subSequence(x..y).reversed()}${this.subSequence(y + 1..<this.length)}"
}

fun String.move(x: Int, y: Int): String {
    val sb = StringBuilder(this)
    val xv = this[x]
    sb.deleteAt(x)
    sb.insert(y, xv)
    return sb.toString()
}