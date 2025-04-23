import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

class D9Test {

    @Test
    fun decompressMarker() {
        assertEquals("ADVENT", "ADVENT".decompressMarker())
        assertEquals("ABBBBBC", "A(1x5)BC".decompressMarker())
        assertEquals("XYZXYZXYZ", "(3x3)XYZ".decompressMarker())
        assertEquals("ABCBCDEFEFG", "A(2x2)BCD(2x2)EFG".decompressMarker())
        assertEquals("(1x3)A", "(6x1)(1x3)A".decompressMarker())
        assertEquals("X(3x3)ABC(3x3)ABCY", "X(8x2)(3x3)ABCY".decompressMarker())
    }

}