import kotlin.test.Test
import kotlin.test.assertEquals

class ConversionCommandTests {
    @Test
    fun testCelsiusToFahrenheit() { // Testing 145 Celsius to Fahrenheit, expect 293
        val conversionCtF: ConversionCommand = ConversionCommand()
        assertEquals(293.0, conversionCtF.convertTemp("1", 145.0))
    }

    @Test
    fun testCelsiusToKelvin() { // Testing 145 Celsius to Kelvin, expect 418.15
        val conversionCtK: ConversionCommand = ConversionCommand()
        assertEquals(418.15, conversionCtK.convertTemp("2", 145.0))
    }

    @Test
    fun testFahrenheitToCelsius() { // Testing 145 Fahrenheit to Celsius, expect 62.78
        val conversionFtC: ConversionCommand = ConversionCommand()
        assertEquals(62.78, conversionFtC. convertTemp("3", 145.0))
    }

    @Test
    fun testFahrenheitToKelvin() { // Testing 145 Fahrenheit to Kelvin, expect 335.93
        val conversionFtK: ConversionCommand = ConversionCommand()
        assertEquals(335.93, conversionFtK.convertTemp("4", 145.0))
    }

    @Test
    fun testKelvinToCelsius() { // Testing 145 Kelvin to Celsius, expect -128.15
        val conversionKtC: ConversionCommand = ConversionCommand()
        assertEquals(-128.15, conversionKtC.convertTemp("5", 145.0))
    }

    @Test
    fun testKelvinToFahrenheit() { // Testing 145 Kelvin to Fahrenheit, expect -198.67
        val conversionKtF: ConversionCommand = ConversionCommand()
        assertEquals(-198.67, conversionKtF.convertTemp("6", 145.0))
    }

    @Test
    fun testValidateUnitTooLarge() { // Testing validation of unit conversion chosen above the range of options available
        val unitValidate: ConversionCommand = ConversionCommand()
        assertEquals(false, unitValidate.validateUnit("8", 7))
    }

    @Test
    fun testValidateUnitTooSmall() {
        val unitValidate: ConversionCommand = ConversionCommand() // Testing validation of unit conversion chosen below the range of options available
        assertEquals(false, unitValidate.validateUnit("0", 7))
    }

    @Test
    fun testValidateUnitExpected() { // Testing validation of unit conversion chosen within the range of options available
        val unitValidate: ConversionCommand = ConversionCommand()
        assertEquals(true, unitValidate.validateUnit("5", 7))
    }

    @Test
    fun testValidateUnitNotInt() { // Testing validation of unit conversion chosen that is not a number option
        val unitValidate: ConversionCommand = ConversionCommand()
        assertEquals(false, unitValidate.validateUnit("banana", 7))
    }

    @Test
    fun testValidateTempBelowCelsius() { // Testing validation of temperature below absolute zero in Celsius
        val testValidate: ConversionCommand = ConversionCommand()
        assertEquals(false, testValidate.validateTemp("-999", -273.15))
    }

    @Test
    fun testValidateTempBelowFahrenheit() { // Testing validation of temperature below absolute zero in Fahrenheit
        val testValidate: ConversionCommand = ConversionCommand()
        assertEquals(false, testValidate.validateTemp("-999", -459.67))
    }

    @Test
    fun testValidateTempBelowKelvin() { // Testing validation of temperature below absolute zero in Kelvin
        val testValidate: ConversionCommand = ConversionCommand()
        assertEquals(false, testValidate.validateTemp("-999", 0.0))
    }

    @Test
    fun testValidateTempNotNumber() { // Testing validation of temperature input not a number
        val testValidate: ConversionCommand = ConversionCommand()
        assertEquals(false, testValidate.validateTemp("apple", 0.0))
    }

    @Test
    fun testValidateTempExpected() { // Testing validation of temperature above absolute zero in Kelvin
        val testValidate: ConversionCommand = ConversionCommand()
        assertEquals(true, testValidate.validateTemp("53", 0.0))
    }

    @Test
    fun testResultToString() { // Testing validation of temperature below absolute zero in Kelvin
        val testResultToString: ConversionCommand = ConversionCommand()
        assertEquals("123.0 degrees Celsius is 253.4 degrees Fahrenheit", testResultToString.resultToString("1", 123.0, 253.4))
    }

}