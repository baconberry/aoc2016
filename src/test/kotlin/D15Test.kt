import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class D15Test {
    @Test
    fun solve() {
        val solver = D15()
        val input = """
            Disc #1 has 5 positions; at time=0, it is at position 4.
            Disc #2 has 2 positions; at time=0, it is at position 1.
        """.trimIndent().split("\n").toTypedArray()
        assertEquals("5", solver.solve(input, 1u))
    }

}