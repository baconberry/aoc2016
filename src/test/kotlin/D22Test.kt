import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import kotlin.streams.toList

class D22Test {


    @Test
    fun hasCycleTest() {
        val noCycles = listOf(1,2,3,4,5,6)
        assertFalse(hasCycle(noCycles))
        val yesCycles = listOf(0,1,2,3,4,5,6,1,2,3,4,5,6)
        assertTrue(hasCycleIterative(yesCycles))
    }

    @Test
    fun realProcessing() {
        val input = Files.lines(File("input.txt").toPath())
            .toList()
            .toTypedArray()
        val solver = D22()
        solver.solve(input, 2u)
    }
}