val bracketsRegex = """(\[[a-zA-Z]+\])""".toRegex()

class D7 : Solver {
    override fun solve(lines: Array<String>, part: UInt): String {
        val result: Int = if (part == 1u) {
            lines
                .count { isIP7(it) }
        } else {
            lines
                .count { it.isAba() }
        }


        return "$result"
    }

    private fun isIP7(it: String): Boolean {
        return !hasAbbaInBrackets(it) && hasAbba(it)
    }

    fun hasAbba(it: String): Boolean {
        return it.split(bracketsRegex)
            .any { it.isAbba() }
    }

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

fun CharSequence.isAba(): Boolean {
    val abaSet = mutableSetOf<CharSequence>()
    this.split(bracketsRegex)
        .forEach { it.getAbas(abaSet) }

    val bracketList = bracketsRegex.findAll(this)
        .map { it.value }
        .toList()
    return abaSet
        .map { "${it[1]}${it[0]}${it[1]}" }
        .any { s ->
            bracketList
                .any { it.contains(s) }
        }
}

fun CharSequence.getAbas(l: MutableSet<CharSequence>) {
    if (this.length < 3) {
        return
    }
    if (this[0] == this[2] && this[0] != this[1]) {
        l.add(this.subSequence(0..<3))
    }
    this.subSequence(1..<this.length).getAbas(l)
}