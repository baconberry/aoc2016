import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class D7Test {
    private val solver = D7()

    @Test
    fun solve() {

        val input = """
            abba[mnop]qrst
            abcd[bddb]xyyx
            aaaa[qwer]tyui
            ioxxoj[asdfgh]zxcvbn
        """.trimIndent().split("\n").toTypedArray()

        assertEquals("2", solver.solve(input, 1u))
    }

    @Test
    fun hasAbba() {
        assertTrue(solver.hasAbba("ioxxoi[iabbax]"))
    }

    @Test
    fun hasAbbaInBrackets() {
        assertTrue(solver.hasAbbaInBrackets("[abba]"))
        assertTrue(solver.hasAbbaInBrackets("ioxxoi[iabbax]"))
        assertFalse(solver.hasAbbaInBrackets("ioxxoi[iabax]"))
    }

    @Test
    fun isAbba() {
        assertTrue("abba".isAbba())
        assertTrue("iabbax".isAbba())
        assertTrue("ioxxoj".isAbba())
        assertTrue("ioxxo".isAbba())
        assertFalse("ioxoj".isAbba())
        assertFalse("abbo".isAbba())
    }

    @Test
    fun isAba() {
        assertTrue("aba[bab]xyz".isAba())
        assertFalse("xyx[xyx]xyx".isAba())
        assertTrue("aaa[kek]eke".isAba())
        assertTrue("zazbz[bzb]cdb".isAba())
    }
}