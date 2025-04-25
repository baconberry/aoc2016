package utils


fun getOneUInt(s: String): UInt {
    val regex = """\d+""".toRegex()
    val match = regex.find(s)
    return match?.value?.toUInt() ?: UInt.MAX_VALUE
}

fun getUIntList(s: CharSequence): List<UInt> {
    val regex = """(\d+)""".toRegex()
    val matches = regex.findAll(s)
    return matches.toList()
        .map { it.value }
        .map { it.toUInt() }
        .toList()
}

fun getIntList(s: CharSequence): List<Int> {
    val regex = """(\d+)""".toRegex()
    val matches = regex.findAll(s)
    return matches.toList()
        .map { it.value }
        .map { it.toInt() }
        .toList()
}

fun List<Int>.toPair(): Pair<Int, Int> {
    return this[0] to this[1]
}

