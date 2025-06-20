import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class D24Test {
    val input = """
        ###########
        #0.1.....2#
        #.#######.#
        #4.......3#
        ###########
    """.trimIndent().split("\n").toTypedArray()

    @Test
    fun solve() {
        val solver = D24()
        assertEquals("14", solver.solve(input, 1u))
    }
}