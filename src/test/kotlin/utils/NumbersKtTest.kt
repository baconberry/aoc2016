package utils

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class NumbersKtTest {

    @Test
    fun getOneUInt() {
        assertEquals(15u, utils.getOneUInt("R15"))
    }
}