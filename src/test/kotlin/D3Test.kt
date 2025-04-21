import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class D3Test {

    @Test
    fun solve() {
        val solver = D3()

        val input = """
            5 10 25
            1 3 3
            1 1 1
            
        """.trimIndent().split("\n").toTypedArray()

        assertEquals("2", solver.solve(input, 1u))
    }
    @Test
    fun solve_Part2() {
        val solver = D3()

        val input = """
            5 10 25
            1 3 3
            1 1 1
            
        """.trimIndent().split("\n").toTypedArray()

        assertEquals("0", solver.solve(input, 2u))
    }
}