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

    @Test
    fun multiMarkerLength() {
        assertEquals(20, "X(8x2)(3x3)ABCY".multiMarkerLength())
        assertEquals(241920, "(27x12)(20x12)(13x14)(7x10)(1x12)A".multiMarkerLength())
        assertEquals(445, "(25x3)(3x3)ABC(2x3)XY(5x2)PQRSTX(18x9)(3x2)TWO(5x7)SEVEN".multiMarkerLength())
    }

}