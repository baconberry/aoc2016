import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class D14Test {

    @Test
    fun miner(){
        val miner = D14.Miner("abc")

        assertEquals(22728, miner.naiveSolution(64))
    }

}