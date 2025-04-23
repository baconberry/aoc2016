import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class D8Test {

    @Test
    fun solve() {
        val solver = D8(3, 7)
        val input = """
            rect 3x2
            rotate column x=1 by 1
            rotate row y=0 by 4
            rotate column x=1 by 1
            
        """.trimIndent().split("\n").toTypedArray()
        assertEquals("6", solver.solve(input, 1u))
    }

    @Test
    fun rotateColumn() {
        val display = createDisplayBoolean(3, 7)
        display.rect(3, 2, true)
        display.rotateColumn(1, 1)
        assertFalse(display[0][1])
        assertTrue(display[1][1])
        assertTrue(display[2][1])
    }

    @Test
    fun rotateRow() {
        val display = createDisplayBoolean(3, 7)
        display.rect(1, 1, true)
        display.rotateRow(0, 2)
        assertFalse(display[0][0])
        assertTrue(display[0][2])
        display.rect(1, 1, true)
        display.rotateRow(0, 2)
        assertFalse(display[0][0])
        assertTrue(display[0][2])
        assertTrue(display[0][4])
    }


}