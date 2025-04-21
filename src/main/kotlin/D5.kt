import java.security.MessageDigest

class D5 : Solver {
    override fun solve(lines: Array<String>, part: UInt): String {
        val word = lines.first()
        val password = StringBuilder()
        val md = MessageDigest.getInstance("MD5")
        var i = 0
        while (true) {
            val md5Val = "$word$i".digest(md)
            if (md5Val.headingZeroes() >= 5u) {
                password.append(md5Val[5])
                if (password.length == 8) {
                    break
                }
            }
            i++
        }
        return password.toString()
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