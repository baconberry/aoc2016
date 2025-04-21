package utils


fun getOneUInt(s: String): UInt {
    val regex = """\d+""".toRegex()
    val match = regex.find(s)
    return match?.value?.toUInt() ?: UInt.MAX_VALUE
}

fun getUIntList(s: String): List<UInt> {
    val regex = """(\d+)""".toRegex()
    val matches = regex.findAll(s)
    return matches.toList()
        .map { it.value }
        .map { it.toUInt() }
        .toList()
}