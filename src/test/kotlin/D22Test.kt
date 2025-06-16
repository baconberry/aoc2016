import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class D22Test {


    @Test
    fun hasCycleTest() {
        val noCycles = listOf(1, 2, 3, 4, 5, 6)
        assertFalse(hasCycle(noCycles))
        val yesCycles = listOf(0, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6)
        assertTrue(hasCycleIterative(yesCycles))
    }

}