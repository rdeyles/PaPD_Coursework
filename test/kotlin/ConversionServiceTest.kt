import kotlin.test.Test
import kotlin.test.assertEquals

class ConversionServiceTest {
    @Test
    fun testCelsiusToKelvin() {
        val conversionCtK: ConversionService = ConversionService()
        assertEquals(418.15, conversionCtK.conversionService("Celsius", "Kelvin", 145.0))
    }

    @Test
    fun testCelsiusToFahrenheit() {
        val conversionCtF: ConversionService = ConversionService()
        assertEquals(293.0, conversionCtF.conversionService("Celsius", "Fahrenheit", 145.0))
    }

    @Test
    fun testFahrenheitToKelvin() {
        val conversionFtK: ConversionService = ConversionService()
        assertEquals(335.93, conversionFtK.conversionService("Fahrenheit", "Kelvin", 145.0))
    }

    @Test
    fun testFahrenheitToCelsius() {
        val conversionFtC: ConversionService = ConversionService()
        assertEquals(62.78, conversionFtC. conversionService("Fahrenheit", "Celsius", 145.0))
    }

    @Test
    fun testKelvinToCelsius() {
        val conversionKtC: ConversionService = ConversionService()
        assertEquals(-128.15, conversionKtC.conversionService("Kelvin", "Celsius", 145.0))
    }

    @Test
    fun testKelvinToFahrenheit() {
        val conversionKtF: ConversionService = ConversionService()
        assertEquals(-198.67, conversionKtF.conversionService("Kelvin", "Fahrenheit", 145.0))
    }
}