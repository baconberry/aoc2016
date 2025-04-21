import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class D5Test {

    @Test
    fun solve() {
        val solver = D5()
        val inputList = listOf(
            "abc" to "18f47a30",
        )
        for ((input, result) in inputList) {
            assertEquals(result, solver.solve(input.split("\n").toTypedArray(), 1u))
        }
    }
}