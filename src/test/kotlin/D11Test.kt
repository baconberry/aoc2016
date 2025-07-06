import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class D11Test {
    @Test
    fun dfs() {
        val f1 = D11.Floor(listOf(), listOf("H", "L"))
        val f2 = D11.Floor(listOf("H"), listOf())
        val f3 = D11.Floor(listOf("L"), listOf())
        val f4 = D11.Floor()
        val building = D11.Building(0, listOf(f1,f2,f3,f4))
        val solver = D11()
        val result = solver.bfs(building)
        assertEquals(9, result)
    }

}