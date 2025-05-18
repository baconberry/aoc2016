import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class D18Test {

    @Test
    fun mazeTest() {
        val input = ".^^.^.^^^^"
        val maze = D18.MineMaze(10, input.length)
        maze.parseFirstRow(input)
        maze.fillMaze()
        assertEquals(38, maze.countSafe())
    }

}