import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class D19Test {

    @Test
    fun takeGifts() {
        val table = D19.RoundTable(5)
        table.fillPersons()
        assertEquals(3, table.takeGifts().idx)
    }
    @Test
    fun takeInFrontGifts() {
        val table = D19.RoundTable(5)
        table.fillPersons()
        assertEquals(2, table.takeFromFront().idx)
    }

}