import org.junit.jupiter.api.Test
import java.io.File
import java.nio.file.Files
import kotlin.test.Ignore

class InputTest {


    @Test
    @Ignore
    fun solveInput(){
        val input = Files.readAllLines(File("input.txt").toPath())
            .toTypedArray()

        val solver = D23()

        val result = solver.solve(input, 1u)
        println(result)
    }
}