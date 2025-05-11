import java.security.MessageDigest

class D14 : Solver {
    override fun solve(lines: Array<String>, part: UInt): String {
        val salt = lines.first()
        val miner = Miner(salt)
        val result = miner.naiveSolution(64)
        return "$result"
    }

    class Miner(val salt: String) {
        val keys = mutableListOf<Int>()
        val charCounter: CharCounter = NaiveCharCounter()

        fun getNextKeys(n: Int): Int {
            val prevKey = if (keys.isEmpty()) {
                -1
            } else {
                keys.last()
            }
            val map = mutableMapOf<Char, Int>()
            var c = prevKey
            val md = MessageDigest.getInstance("MD5")
            while (keys.size < n) {
                val nextKey = c + 1

                val md5 = "$salt$nextKey".digest(md).lowercase()
                for (c5 in charCounter.get5Chars(md5)) {
                    val c5v = map[c5]
                    if (c5v != null && (nextKey - c5v) <= 1000) {
                        keys.add(nextKey)
                    }
                }
                for (c3 in charCounter.get3Chars(md5)) {
                    map[c3] = nextKey
                }

                c = nextKey
            }
            return c
        }

        fun naiveSolution(n: Int): Int {
            var counter = 0
            val md = MessageDigest.getInstance("MD5")
            val pairList = mutableListOf<Pair<Int, Int>>()
            for (c in 0..Int.MAX_VALUE) {
                val md5 = "$salt$c".digest(md).lowercase()
                val c3 = charCounter.get3Chars(md5)
                if (c3.isNotEmpty()) {
                    val idx5 = has5chars(c + 1, c3.first(), md)
                    if (idx5 != null) {
                        pairList.add(c to idx5)
                        pairList.sortWith(Comparator.comparing { it.second })
                        counter++
                    }
                }
                if (counter == n +1001) {
                    return pairList[63].first
                }
            }
            return Int.MAX_VALUE
        }

        private fun has5chars(start: Int, c3: Char, md: MessageDigest): Int? {
            for (c in start..start + 1000) {
                val md5 = "$salt$c".digest(md).lowercase()
                val c5 = charCounter.get5Chars(md5)
                if (c3 in c5) {
                    return c
                }
            }
            return null
        }

    }

    interface CharCounter {
        fun get3Chars(s: String): List<Char>
        fun get5Chars(s: String): List<Char>
    }

    class RegexCharCounter : CharCounter {
        val c5Regex = """(\w)(\1{4})""".toRegex()
        override fun get5Chars(md5: String): List<Char> {
            return c5Regex.findAll(md5)
                .map { it.value[0] }
                .take(1)
                .toList()
        }

        val c3Regex = """(\w)(:?\1{2})""".toRegex()
        override fun get3Chars(md5: String): List<Char> {
            return c3Regex.findAll(md5)
                .map { it.value[0] }
                .take(1)
                .toList()
        }
    }

    class NaiveCharCounter : CharCounter {
        fun getNChars(s: String, n: Int): List<Char> {
            if (s.isEmpty()) {
                return listOf()
            }
            val result = mutableListOf<Char>()
            var prevChar = s[0]
            var count = 1
            for (c in s.slice(1..<s.length)) {
                if (prevChar == c) {
                    count++
                } else {
                    count = 1
                }
                if (count == n) {
                    result.add(c)
                    count = 0
                }
                prevChar = c
            }
            return result
        }

        override fun get3Chars(s: String): List<Char> {
            return getNChars(s, 3).take(1)
        }

        override fun get5Chars(s: String): List<Char> {
            return getNChars(s, 5)
        }

    }
}