import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class D21Test {
    val lines = """
        swap position 4 with position 0
        swap letter d with letter b
        reverse positions 0 through 4
        rotate left 1
        move position 1 to position 4
        move position 3 to position 0
        rotate based on position of letter b
        rotate based on position of letter d
        """.trimIndent()
    @Test
    fun solve() {
        val solver = D21()
        assertEquals("decab", solver.solve("abcde", lines.split("\n").toTypedArray()))
    }

    @Test
    fun solveUnscramble() {
        val solver = D21()
        solver.reversed=true
        assertEquals("abcde", solver.solve("decab", lines.split("\n").reversed().toTypedArray()))
    }
}