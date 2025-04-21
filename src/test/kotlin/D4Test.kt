import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class D4Test {

    @Test
    fun solve() {
        val solver = D4()

        val input = """
            aaaaa-bbb-z-y-x-123[abxyz]
            a-b-c-d-e-f-g-h-987[abcde]
            not-a-real-room-404[oarel]
            totally-real-room-200[decoy]
            
        """.trimIndent().split("\n").toTypedArray()

        assertEquals("1514", solver.solve(input, 1u))
    }
}