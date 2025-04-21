import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class D3Test {

    @Test
    fun solve() {
        val solver = D3()

        val input = """
            5 10 25
            1 3 3
            
        """.trimIndent().split("\n").toTypedArray()

        assertEquals("1", solver.solve(input, 2u))
    }
}