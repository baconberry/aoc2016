import kotlin.math.pow

class D25 : Solver {
    override fun solve(lines: Array<String>, part: UInt): String {
        val instructions = lines
            .filter { it.isNotEmpty() }
            .toList()

        for (i in 0..100) {
            val r = Registers(i)
            start(r)
            println(r.out)
        }

        return "0"
    }

    fun start(r: Registers) {
        r.d = r.a
        r.c = 9
        r.b = 282
        do {
            while (r.b > 0) {
                r.d++
                r.b--
            }
            r.c--
        } while (r.c > 0)
        secA(r)
    }

    private fun secA(r: Registers) {
        r.a = r.d
        secB(r)
    }

    private fun secB(r: Registers) {
        r.b = r.a
        r.a = 0
        secC(r)
    }

    private fun secC(r: Registers) {
        r.c = 2
        do {
            if (r.b > 0) {
                r.b--
                r.c--
            } else {
                secD(r)
                break
            }
        } while (r.c > 0)
        r.a++
    }

    fun secD(r: Registers) {
        r.d = 2
        while (true) {
            if (r.c > 0) {
                r.b--
                r.c--
            } else {
                r.out.append("${r.b}")
                if (r.out.length >= r.maxOut) {
                    break
                }
                if (r.a > 0) {
                    secB(r)
                } else {
                    secA(r)
                }
                break
            }
        }
    }


    class Registers(
        var a: Int = 0,
        var b: Int = 0,
        var c: Int = 0,
        var d: Int = 0,
        val out: StringBuilder = StringBuilder(),
        val maxOut: Int = 100
    )
}