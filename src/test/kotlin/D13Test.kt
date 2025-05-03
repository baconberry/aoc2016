import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class D13Test {

    @Test
    fun lazyMap() {
        val lazyMap = D13.LazyBinaryMap(10)
        assertEquals(11, lazyMap.shortestPath(1 to 1, 7 to 4))
    }

}