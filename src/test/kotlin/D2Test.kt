import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

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

    @Test
    fun solve_Part2() {
        val solver = D2()

        val input = """
            ULL
            RRDDD
            LURDL
            UUUUD
            
        """.trimIndent().split("\n").toTypedArray()

        assertEquals("5DB3", solver.solve(input, 2u))
    }
}