import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class D2Test {

    @Test
    fun solve() {
        val solver = D2()

        val input = """
            ULL
            RRDDD
            LURDL
            UUUUD
            
        """.trimIndent().split("\n").toTypedArray()

        assertEquals("1985", solver.solve(input, 1u))
    }
}