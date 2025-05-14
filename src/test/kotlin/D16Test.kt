import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class D16Test {

    @Test
    fun solve() {
        val solver = D16()
        val result = solver.solve("10000", 20)
        assertEquals("01100", result)
    }

    @Test
    fun dragonCurve() {
        val expected = listOf<Pair<String, String>>(
            "1" to "100",
            "0" to "001",
            "11111" to "11111000000",
            "111100001010" to "1111000010100101011110000"
        )
        expected.forEach { (a, expected) ->
            assertEquals(expected, a.dragonCurve())
        }
    }
}