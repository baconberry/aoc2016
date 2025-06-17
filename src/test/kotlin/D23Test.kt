import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class D23Test {
    val input = """
        cpy 2 a
        tgl a
        tgl a
        tgl a
        cpy 1 a
        dec a
        dec a
    """.trimIndent().split("\n").toTypedArray()

    @Test
    fun solve() {
        val emu = Emu(input.toList())
        emu.execute()
        assertEquals(3, emu.registers["a"])
    }
}