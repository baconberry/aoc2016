import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import kotlin.test.Ignore

class D5Test {

    @Test
    @Ignore("Slow test")
    fun solve() {
        val solver = D5()
        val inputList = listOf(
            "abc" to "18f47a30",
        )
        for ((input, result) in inputList) {
            assertEquals(result, solver.solve(input.split("\n").toTypedArray(), 1u))
        }
    }

    @Test
    @Ignore("Slow test")
    fun solve_Part2() {
        val solver = D5()
        val inputList = listOf(
            "abc" to "05ace8e3",
        )
        for ((input, result) in inputList) {
            assertEquals(result, solver.solve(input.split("\n").toTypedArray(), 2u))
        }
    }
}