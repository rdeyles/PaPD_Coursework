import ppdcoursework.SumCommand
import kotlin.test.Test
import kotlin.test.assertEquals

class SumCommandTests {


    @Test
    fun makeValidSum() {
        val sc = SumCommand()
        val result = sc.makeSum(3)
        assertEquals(36u,result)
    }

    @Test
    fun makeLargestValidSum() {
        val sc = SumCommand()
        val result = sc.makeSum(92161)
        assertEquals(18035913638884423681u,result)
    }

    @Test
    fun makeSmallestValidSum() {
        val sc = SumCommand()
        val result = sc.makeSum(1)
        assertEquals(1u,result)
    }

}