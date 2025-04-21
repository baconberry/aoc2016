package utils


fun getOneUInt(s: String): UInt {
    val regex = """\d+""".toRegex()
    val match = regex.find(s)
    return match?.value?.toUInt() ?: UInt.MAX_VALUE
}