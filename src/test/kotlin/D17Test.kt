import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class D17Test {

    @Test
    fun md5MazeMin() {
        val maze = D17.MD5Maze(4, 4, "ihgpwlah")
        assertEquals("ihgpwlahDDRRRD", maze.minPath(0 to 0))
    }
}