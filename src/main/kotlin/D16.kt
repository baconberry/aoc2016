class D16(val minLen: Int) : Solver {
    override fun solve(lines: Array<String>, part: UInt): String {
        val input = lines.first()
        val dc = input.dragonCurve(minLen)
        var result = dc.dragonChecksum()
        while (result.length % 2 == 0) {
            result = result.dragonChecksum()
        }
        return "$result"
    }

}


fun CharSequence.flipAll(): CharSequence {
    val flipped = StringBuilder()
    for (c in this) {
        if (c == '1') {
            flipped.append('0')
        } else {
            flipped.append('1')
        }
    }
    return flipped.toString()
}

fun CharSequence.dragonCurve(): CharSequence {
    var b = this.reversed()
    b = b.flipAll()
    return "${this}0$b"
}

fun CharSequence.dragonCurve(minLength: Int): CharSequence {
    var a = this
    while (a.length < minLength) {
        a = a.dragonCurve()
    }
    return a.subSequence(0..<minLength)
}

fun CharSequence.dragonChecksum(): CharSequence {
    var i = 0
    val result = StringBuilder()
    while (i < this.length) {
        if (this[i] == this[i + 1]) {
            result.append('1')
        } else {
            result.append('0')
        }
        i += 2
    }
    return result
}
