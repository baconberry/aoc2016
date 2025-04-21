import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class D1Test {

    @Test
    fun solve() {
        val solver = D1()
        val inputList = listOf(
            "R2, L3" to "5",
            "R2, R2, R2" to "2",
            "R5, L5, R5, R3" to "12",
        )
        for ((input, result) in inputList) {
            assertEquals(result, solver.solve(input.split("\n").toTypedArray(), 1u))
        }
    }

    @Test
    fun solve_Part2() {
        val solver = D1()
        val inputList = listOf(
            "R8, R4, R4, R8" to "4",
        )
        for ((input, result) in inputList) {
            assertEquals(result, solver.solve(input.split("\n").toTypedArray(), 2u))
        }
    }
}