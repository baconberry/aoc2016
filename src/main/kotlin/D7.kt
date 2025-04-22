class D7 : Solver {
    override fun solve(lines: Array<String>, part: UInt): String {
        val result = lines
            .count { isIP7(it) }

        return "$result"
    }

    private fun isIP7(it: String): Boolean {
        return !hasAbbaInBrackets(it) && hasAbba(it)
    }

    fun hasAbba(it: String): Boolean {
        return it.split(bracketsRegex)
            .any { it.isAbba() }
    }

    val bracketsRegex = """(\[[a-zA-Z]+\])""".toRegex()
    fun hasAbbaInBrackets(it: String): Boolean {
        return bracketsRegex.findAll(it)
            .any { it.value.subSequence(1, it.value.length - 1).toString().isAbba() }

    }
}

fun String.isAbba(): Boolean {
    if (this.length < 4) {
        return false
    }
    return (
            this[0] != this[1]
                    && this.subSequence(0..<2).contentEquals(this.subSequence(2..<4).reversed())
            )
            || this.subSequence(1..<this.length).toString().isAbba()

}