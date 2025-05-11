import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class D14Test {

    @Test
    fun miner(){
        val miner = D14.Miner("abc", D14.MD5Hasher())

        assertEquals(22728, miner.naiveSolution(64))
    }

    @Test
    fun miner_stretched(){
        val miner = D14.Miner("abc", D14.MD5StretchedHasher())

        assertEquals(22551, miner.naiveSolution(64))
    }

}