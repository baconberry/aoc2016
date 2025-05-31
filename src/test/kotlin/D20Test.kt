import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class D20Test {

    @Test
    fun testSmallBlacklist() {
        val blacklist = D20.IPBlackList()
        blacklist.addRange(5L..8L)
        blacklist.addRange(0L..2L)
        blacklist.addRange(4L..7L)

        assertEquals(3, blacklist.lowestAllowedNaive())
    }

}