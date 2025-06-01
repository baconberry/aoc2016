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

    @Test
    fun longRangeSplit(){
        val a = 0L..4294967295L
        val b = 919958672L..920375477L
        val split = a split b
        assertEquals(2, split.size)
        assertTrue(split.contains(0L..919958671L))
        assertTrue(split.contains(920375478L..4294967295L))

        val c = 0L..10L
        val d = 11L..15L
        val splitcd = c split d
        assertEquals(1, splitcd.size)
        assertEquals(listOf(c), splitcd)
    }

}