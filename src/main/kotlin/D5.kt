import java.security.MessageDigest

class D5 : Solver {
    override fun solve(lines: Array<String>, part: UInt): String {
        val word = lines.first()
        val password = StringBuilder()
        val passwordArr = MutableList(8) { "" }
        val md = MessageDigest.getInstance("MD5")
        var i = 0
        while (true) {
            val md5Val = "$word$i".digest(md)
            if (md5Val.headingZeroes() >= 5u) {
                if (part == 2u) {
                    if (part2Password(md5Val, passwordArr, password)) break
                } else {
                    password.append(md5Val[5])
                    if (password.length == 8) {
                        break
                    }
                }
            }
            i++
        }
        return password.toString()
    }

    private fun part2Password(
        md5Val: String,
        passwordArr: MutableList<String>,
        password: StringBuilder
    ): Boolean {
        val pos = md5Val[5].digitToInt(16)
        val char = md5Val[6]
        if (pos in 0..7 && passwordArr[pos] == "") {
            passwordArr[pos] = "$char"
        }
        if (passwordArr.notBlankCount() == 8) {
            password.append(passwordArr.joinToString(""))
            return true
        }
        return false
    }
}

@OptIn(ExperimentalStdlibApi::class)
fun String.digest(md: MessageDigest): String {
    val digest = md.digest(this.toByteArray())
    return digest.toHexString()
}

fun String.headingZeroes(): UInt {
    var count = 0u
    for (char in this.chars()) {
        if (char != '0'.code) {
            break
        }
        count++
    }
    return count
}

fun List<String>.notBlankCount(): Int {
    return this.count { it.isNotBlank() }
}