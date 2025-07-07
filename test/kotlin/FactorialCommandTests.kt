import ppdcoursework.FactorialCommand
import java.math.BigInteger
import kotlin.test.Test
import kotlin.test.assertEquals

class FactorialCommandTests {

    @Test
    fun makeFactorial() {
        val fc = FactorialCommand()
        val sum = fc.makeFactorial(listOf(1, 2, 3))
        assertEquals(BigInteger("9"),sum)
    }

    @Test
    fun makeAnotherFactorial(){
        val fc = FactorialCommand()
        val sum = fc.makeFactorial(listOf(5, 5, 3))
        assertEquals(BigInteger("246"),sum)
    }


}