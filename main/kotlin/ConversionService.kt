import kotlin.math.round

class ConversionService {
    /**
     * Converts between units of temperature, chosen from Celsius, Fahrenheit and Kelvin.
     *
     * Separation of concerns: input validation handled by temperatureConversion() -- no values below absolute zero should be passed as arguments.
     *
     * ## Examples:
     *
     * conversionService("Kelvin", "Celsius", 256.0) = -17.15
     *
     * conversionService("Fahrenheit", "Celsius", -143.2) = -97.33
     *
     * @param firstUnit -- temperature unit to convert from.
     * @param secondUnit -- temperature unit to convert to.
     * @param temperatureToConvert -- temperature value to convert from.
     * @return temperatureConverted -- converted temperature rounded to 2 decimal places.
     */
    fun conversionService(firstUnit: String, secondUnit: String, temperatureToConvert: Double): Double {
        var temperatureConverted: Double = 0.0
        when (firstUnit) {
            "Celsius" if secondUnit == "Fahrenheit" -> temperatureConverted = temperatureToConvert * 9 / 5 + 32
            "Fahrenheit" if secondUnit == "Celsius" -> temperatureConverted = (temperatureToConvert - 32) * 5 / 9
            "Celsius" if secondUnit == "Kelvin" -> temperatureConverted = temperatureToConvert + 273.15
            "Kelvin" if secondUnit == "Celsius" -> temperatureConverted = temperatureToConvert - 273.15
            "Fahrenheit" if secondUnit == "Kelvin" -> temperatureConverted = (temperatureToConvert - 32) * 5 / 9 + 273.15
            "Kelvin" if secondUnit == "Fahrenheit" -> temperatureConverted = (temperatureToConvert - 273.15) * 9 / 5 + 32
        }
        temperatureConverted = round(temperatureConverted * 100.0) / 100.0
        return temperatureConverted
    }

    /**
     * Gathers user input and performs input validation before calling conversionService():
     *
     * Input units must be from given options and input temperature must be no less than absolute zero.
     *
     * In the unlikely case of attempting to convert an extremely large temperature, input values exceeding 1,193,946,452 may return inaccurate conversions.
     *
     * User input, no parameters.
     *
     * ## Examples:
     *
     * User inputs request 125 degrees Celsius to Kelvin --> "125 degrees Celsius = 398.15 degrees Kelvin."
     *
     * User inputs request -125 degrees Kelvin to Fahrenheit --> "Please enter a number, ensure the number is above 0 degrees Kelvin: "
     *
     * @return String -- either "Exit" when user decides to terminate the process early, or outlining the results of the conversion with the return value of conversionService().
     */
    fun temperatureConversion(): String {
        val units: Array<String> = arrayOf("1", "2", "3", "Exit") // Listing choice of units as numbers for user-friendly inputs. Exit left as a word so accidental exit is unlikely.
        val unitsToPresent: Array<String> = arrayOf("Celsius", "Fahrenheit", "Kelvin") // Listing units as words for messages to user and passing to conversionService().
        println("""You are able to convert between the following units:
        |1. Celsius
        |2. Fahrenheit
        |3. Kelvin
    """.trimMargin())
        var confirm: String = ""
        var firstUnit: String = ""
        var secondUnit: String = ""

        /* Validation to ensure units chosen are from the options in the array.
        Loop ends when user confirms they are happy with the chosen units.
         */
        while (confirm != "Y") {
            firstUnit = getUserInput("Please enter the number of which unit you wish to convert from (type Exit if you wish to go back to the main menu): ")
            while (firstUnit !in units) {
                firstUnit = getUserInput("That is not one of the choices, please type 1, 2, 3 or Exit if you want to go back.")
            }
            if (firstUnit == "Exit") {
                return "Exit"
            }
            secondUnit = getUserInput("Please enter the number of which unit you wish to convert to (type Exit if you wish to go back to the main menu): ")
            while (secondUnit !in units || secondUnit == firstUnit) {
                if (secondUnit !in units) {
                    secondUnit = getUserInput("That is not one of the choices, please type either 1, 2, 3, or Exit if you want to go back.")
                }
                else if (secondUnit == firstUnit) {
                    secondUnit = getUserInput("You already picked this unit to convert from. Pick another option or type Exit if you want to go back.") // User can't choose the same units twice. It would be meaningless.
                }
            }
            if (secondUnit == "Exit") {
                return "Exit"
            }
            println("You have selected to convert ${unitsToPresent[firstUnit.toInt() - 1]} to ${unitsToPresent[secondUnit.toInt() - 1]}.") // Convert user inputs to int for accessing worded temp unit elements.
            confirm = getUserInput("Please confirm if this is correct (Y/N): ")
            while (confirm != "Y" && confirm != "N") { // Avoiding any inputs other than Y or N
                confirm = getUserInput("Please enter Y for yes or N for no: ")
            }
        }
        var temperatureInput: String = getUserInput("Please enter the temperature to convert: ")
        when (firstUnit) { // Validating input can be converted to Double and is above absolute zero (coldest possible temp).
            "1" -> {
                while (temperatureInput.toDoubleOrNull() == null || temperatureInput.toDouble() < -273.15) {
                    temperatureInput = getUserInput("Please enter a number, ensure the value is above -273.15 degrees Celsius: ")
                }
            }
            "2" -> {
                while (temperatureInput.toDoubleOrNull() == null || temperatureInput.toDouble() < -459.67) {
                    temperatureInput = getUserInput("Please enter a number, ensure the value is above -459.67 degrees Fahrenheit: ")
                }
            }
            "3" -> {
                while (temperatureInput.toDoubleOrNull() == null || temperatureInput.toDouble() < 0) {
                    temperatureInput = getUserInput("Please enter a number, ensure the number is above 0 degrees Kelvin: ")
                }
            }
        }
        val temperatureToConvert: Double = temperatureInput.toDouble()
        firstUnit = unitsToPresent[firstUnit.toInt() - 1]
        secondUnit = unitsToPresent[secondUnit.toInt() - 1]
        return ("$temperatureToConvert degrees $firstUnit = ${conversionService(firstUnit, secondUnit, temperatureToConvert)} degrees $secondUnit.")
    }
}