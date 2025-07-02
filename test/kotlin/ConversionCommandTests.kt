import kotlin.test.Test
import kotlin.test.assertEquals

class ConversionCommandTests {
    @Test
    fun testCelsiusToKelvin() {
        val conversionCtK: ConversionCommand = ConversionCommand()
        assertEquals(418.15, conversionCtK.conversionService("Celsius", "Kelvin", 145.0))
    }

    @Test
    fun testCelsiusToFahrenheit() {
        val conversionCtF: ConversionCommand = ConversionCommand()
        assertEquals(293.0, conversionCtF.conversionService("Celsius", "Fahrenheit", 145.0))
    }

    @Test
    fun testFahrenheitToKelvin() {
        val conversionFtK: ConversionCommand = ConversionCommand()
        assertEquals(335.93, conversionFtK.conversionService("Fahrenheit", "Kelvin", 145.0))
    }

    @Test
    fun testFahrenheitToCelsius() {
        val conversionFtC: ConversionCommand = ConversionCommand()
        assertEquals(62.78, conversionFtC. conversionService("Fahrenheit", "Celsius", 145.0))
    }

    @Test
    fun testKelvinToCelsius() {
        val conversionKtC: ConversionCommand = ConversionCommand()
        assertEquals(-128.15, conversionKtC.conversionService("Kelvin", "Celsius", 145.0))
    }

    @Test
    fun testKelvinToFahrenheit() {
        val conversionKtF: ConversionCommand = ConversionCommand()
        assertEquals(-198.67, conversionKtF.conversionService("Kelvin", "Fahrenheit", 145.0))
    }
}