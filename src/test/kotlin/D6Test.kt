import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class D6Test {

    @Test
    fun solve() {
        val solver = D6()

        val input = """
            eedadn
            drvtee
            eandsr
            raavrd
            atevrs
            tsrnev
            sdttsa
            rasrtv
            nssdts
            ntnada
            svetve
            tesnvt
            vntsnd
            vrdear
            dvrsen
            enarar
        """.trimIndent().split("\n").toTypedArray()

        assertEquals("easter", solver.solve(input, 1u))
    }
}