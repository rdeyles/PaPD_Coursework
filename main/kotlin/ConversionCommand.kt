import kotlin.math.round

/**
 * The ConversionCommand class is designed to convert between two units of temperature.
 *
 * It interacts with the user to obtain input, validates it,
 * converting between the units and provided temperature value,
 * returning a formatted string result or an exit message.
 */
class ConversionCommand:Command {
    /**
     * Validates the unit choice to ensure it matches one of the options in the menu.
     *
     * Converts unit input to an int or null. If outside the range of options or not int, automatically returns false.
     *
     * ## Examples:
     *
     * validate("7", 7) = true
     *
     * validate("banana", 7) = false
     *
     * validate("8", 7) = false
     *
     * @param unitChoice -- chosen option from the ConversionCommand menu to validate.
     *
     * @param maxChoice -- maximum number fo choices available.
     *
     * @return Boolean -- true if the unit input matches any of the available choices in the ConversionCommand menu.
     */
    fun validateUnit(unitChoice: String, maxChoice: Int): Boolean {
        return when {
            unitChoice.toIntOrNull() == null -> false // Input cannot be converted to Int
            unitChoice.toInt() < 1 -> false // Input is below the lowest option of 1
            unitChoice.toInt() > maxChoice -> false // Input is beyond the range of available options
            else -> true // Input is valid
        }
    }

    /**
     * Validates the temperature input value.
     *
     * Ensures the value is a valid number above absolute zero in the chosen temperature.
     *
     * ## Examples:
     *
     * validateTemp("banana", -273.15) = false
     *
     * validateTemp("-555", -273.15) = false
     *
     * validateTemp("456", -273.15) = true
     *
     * @param tempInput -- temperature value to convert
     *
     * @param minTemp -- the minimum temperature (absolute zero)
     *
     * @return Boolean -- true if the temperature input is possible to convert to Double type and above absolute zero.
     */
    fun validateTemp(tempInput: String, minTemp: Double): Boolean {
        return when {
            tempInput.toDoubleOrNull() == null -> false // Input cannot be converted to Double
            tempInput.toDouble() < minTemp -> false // Input is below absolute zero
            else -> true // Input is valid
        }
    }

    /**
     * Converts between units of temperature, chosen from Celsius, Fahrenheit and Kelvin.
     *
     * ## Examples:
     *
     * conversionService("5", 256.0) = -17.15
     *
     * conversionService("3", -143.2) = -97.33
     *
     * @param unitChoice -- chosen units to convert.
     * @param tempToConvert -- temperature to convert from as Double type.
     * @return temperatureConverted -- converted temperature rounded to 2 decimal places.
     */
    fun convertTemp(unitChoice: String, tempToConvert: Double): Double {
        var temperatureConverted: Double = 0.0
        temperatureConverted = when (unitChoice) {
            "1" -> tempToConvert * 9 / 5 + 32 // Celsius to Fahrenheit.
            "2" -> tempToConvert + 273.15 // Celsius to Kelvin.
            "3" -> (tempToConvert - 32) * 5 / 9 // Fahrenheit to Celsius
            "4" -> (tempToConvert - 32) * 5 / 9 + 273.15 // Fahrenheit to Kelvin.
            "5" -> tempToConvert - 273.15 // Kelvin to Celsius.
            "6" -> (tempToConvert - 273.15) * 9 / 5 + 32 // Kelvin to Fahrenheit.
            else -> -999.9 /* Redundancy returning an arbitrary value below absolute zero in any units.
            validation will not allow any value this low to be passed to conversionService().
            Serving only to ensure the when statement is exhaustive. */
        }
        temperatureConverted = round(temperatureConverted * 100.0) / 100.0
        return temperatureConverted
    }

    /**
     * Prepares the units into string variables to be entered into a string separately.
     *
     * Uses the ConversionCommand menu choices as a reference (e.g., "1" indicates Celsius to Fahrenheit)
     *
     * Creates a message to return the result of the temperature conversion.
     *
     * ## Examples:
     *
     * resultToString("1", 123.0, 253.4) = "123.0 degrees Celsius is 253.4 degrees Fahrenheit"
     *
     * @param unitChoice -- chosen option from the ConversionCommand menu to split into the two units.
     *
     * @param tempToConvert -- temperature value to convert
     *
     * @param tempResult -- result value of temperature conversion
     *
     * @return String -- message highlighting the results of the temperature conversion
     */
    fun resultToString(unitChoice: String, tempToConvert: Double, tempResult: Double): String {
        val firstUnit: String = when (unitChoice) { // Set the first unit for the output message.
            "1", "2" -> "Celsius"
            "3", "4" -> "Fahrenheit"
            "5", "6" -> "Kelvin"
            else -> EMPTY_MESSAGE
        }
        val secondUnit: String = when (unitChoice) { // Set the second unit for the output message.
            "3", "5" -> "Celsius"
            "1", "6" -> "Fahrenheit"
            "2", "4" -> "Kelvin"
            else -> EMPTY_MESSAGE
        }
        return "$tempToConvert degrees $firstUnit is $tempResult degrees $secondUnit"
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
     * @return String -- either "Exiting the temperature process" when user decides to terminate the process early, or outlining the results of the conversion with the return value of conversionService().
     */
    override fun execute(): String {
        // ConversionCommand main menu.
        printMessage("Please choose one of the following:",4,NEW_LINE)
        printMessage("1. Celsius to Fahrenheit",4,NEW_LINE)
        printMessage("2. Celsius to Kelvin",4,NEW_LINE)
        printMessage("3. Fahrenheit to Celsius",4,NEW_LINE)
        printMessage("4. Fahrenheit to Kelvin",4,NEW_LINE)
        printMessage("5. Kelvin to Celsius",4,NEW_LINE)
        printMessage("6. Kelvin to Fahrenheit",4,NEW_LINE)
        printMessage("7. Exit",4,NEW_LINE)
        var unitChoice: String = getUserInput("Enter the option number:")
        val maxChoice: Int = 7 // Notes the current maximum number of options.
        while(!validateUnit(unitChoice, maxChoice)) { // Unit choice must be written as an integer, below the max number of choices.
            unitChoice = getUserInput("Invalid input, enter a number from the options.")
        }
        if (unitChoice.toInt() == maxChoice) { // Exits the command early.
            return "Exiting the temperature conversion process."
        }
        var tempChoice: String = getUserInput("Enter the value you wish to convert:")
        if (isExitCommand(tempChoice)) { // Exits the command early.
            return "Exiting the temperature conversion process."
        }
        val minTemp: Double = when (unitChoice) { // Sets the minimum temperature at absolute zero.
            "1", "2" -> -273.15 // Celsius.
            "3", "4" -> -459.67 // Fahrenheit.
            "5", "6" -> 0.0 // Kelvin.
            else -> 0.0 // Redundancy, set to the highest value of absolute zero.
        }
        while (!validateTemp(tempChoice, minTemp)) { // Temperature choice must be above maximum zero based on the first unit in the chosen conversion.
            tempChoice = getUserInput("Invalid input, enter a number larger than ${minTemp}:")
        }
        val tempToConvert: Double = tempChoice.toDouble() // Convert tempChoice to double for computation in convertTemp()
        val tempResult: Double = convertTemp(unitChoice, tempToConvert) // Call convertTemp() to compute conversion.
        return resultToString(unitChoice, tempToConvert, tempResult) // Return the computation result as a string message.
    }

}