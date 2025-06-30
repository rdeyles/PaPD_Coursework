import kotlin.math.round

class ConversionService {
    /**
     * Converts between units of temperature, chosen from Celsius, Fahrenheit and Kelvin.
     *
     * All user input handling and validation is handled by temperatureConversion(). conversionService() handles the computation.
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
        temperatureConverted = when (Pair(firstUnit, secondUnit)) {
            Pair("Celsius", "Fahrenheit") -> temperatureToConvert * 9 / 5 + 32
            Pair("Fahrenheit", "Celsius") -> (temperatureToConvert - 32) * 5 / 9
            Pair("Celsius", "Kelvin") -> temperatureToConvert + 273.15
            Pair("Kelvin", "Celsius") -> temperatureToConvert - 273.15
            Pair("Fahrenheit", "Kelvin") -> (temperatureToConvert - 32) * 5 / 9 + 273.15
            Pair("Kelvin", "Fahrenheit") -> (temperatureToConvert - 273.15) * 9 / 5 + 32
            else -> -999.9 /* Redundancy returning an arbitrary value below absolute zero in any units.
            temperatureConversion() will not allow any value this low to be passed to conversionService().
            Serving only to ensure the when statement is exhaustive. */
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
     * There are no parameters, the function requests user input throughout execution.
     *
     * ## Examples:
     *
     * User inputs request 125 degrees Celsius to Kelvin --> function calls conversionService, then returns "125 degrees Celsius = 398.15 degrees Kelvin."
     *
     * User inputs request -125 degrees Kelvin to Fahrenheit --> function calls conversionService, then returns "Please enter a number, ensure the number is above 0 degrees Kelvin: "
     *
     * @return String -- either "Exiting the temperature process" when user decides to terminate the process early, or outlining the results of the conversion with the return value of conversionService().
     */
    fun temperatureConversion(): String {
        val units: Array<String> = arrayOf("1", "2", "3") // Listing choice of units as numbers for user-friendly inputs.
        val exitOptions: Array<String> = arrayOf("Exit", "exit", "End", "end", "Cancel", "cancel", "Stop", "stop") // Allow a few likely exit commands.
        val unitsToPresent: Array<String> = arrayOf("Celsius", "Fahrenheit", "Kelvin") // Listing units as words for messages to user and passing to conversionService().
        println("""You are able to convert between the following units:
        |1. Celsius
        |2. Fahrenheit
        |3. Kelvin
        |
        |At any time, type exit to end the temperature conversion process early.
    """.trimMargin())
        var confirm: String = ""
        var firstUnit: String = ""
        var secondUnit: String = ""

        /* Validation to ensure units chosen are from the options in the array.
        Loop ends when user confirms they are happy with the chosen units.
        Exit function if user inputs "Exit" or "exit" at any stage.
        Must be the full word to avoid accidental exits.
         */
        while (confirm !in arrayOf("Y", "y")) {
            firstUnit = getUserInput("Please enter the number of which unit you wish to convert from: ")
            while (firstUnit !in units) {
                if (firstUnit in exitOptions) {
                    return "Exiting the temperature conversion process."
                }
                else {
                    firstUnit = getUserInput("That is not one of the choices, please type 1, 2, 3: ")
                }
            }
            secondUnit = getUserInput("Please enter the number of which unit you wish to convert to: ")
            while (secondUnit !in units || secondUnit == firstUnit) {
                if (secondUnit !in units) {
                    if (secondUnit in exitOptions) {
                        return "Exiting the temperature conversion process."
                    }
                    else {
                        secondUnit = getUserInput("That is not one of the choices, please type either 1, 2, 3: ")
                    }
                }
                else if (secondUnit == firstUnit) {
                    secondUnit = getUserInput("You already picked this unit to convert from. Pick another option: ") // User can't choose the same units twice. It would be meaningless.
                }
            }
            println("You have selected to convert ${unitsToPresent[firstUnit.toInt() - 1]} to ${unitsToPresent[secondUnit.toInt() - 1]}.") // Convert user inputs to int for accessing worded temp unit elements.
            confirm = getUserInput("Please confirm if this is correct (Y/N): ")
            while (confirm !in arrayOf("Y", "y", "N", "n")) { // Avoiding any inputs other than Y or N, allow lowercase.
                if (confirm in exitOptions) {
                    return "Exiting the temperature conversion process."
                }
                else {
                    confirm = getUserInput("Please enter Y for yes, N for no, or Exit: ")
                }
            }
        }
        var temperatureInput: String = getUserInput("Please enter the temperature to convert: ")
        when (firstUnit) { // Validating input can be converted to Double and is above absolute zero (coldest possible temperature). Exit condition included at each branch.
            "1" -> {
                while (temperatureInput.toDoubleOrNull() == null || temperatureInput.toDouble() < -273.15) {
                    if (temperatureInput in exitOptions) {
                        return "Exiting the temperature conversion process."
                    }
                    else {
                        temperatureInput = getUserInput("Please enter a number, ensure the value is above -273.15 degrees Celsius: ")
                    }
                }
            }
            "2" -> {
                while (temperatureInput.toDoubleOrNull() == null || temperatureInput.toDouble() < -459.67) {
                    if (temperatureInput in exitOptions) {
                        return "Exiting the temperature conversion process."
                    }
                    else {
                        temperatureInput = getUserInput("Please enter a number, ensure the value is above -459.67 degrees Fahrenheit: ")
                    }
                }
            }
            "3" -> {
                while (temperatureInput.toDoubleOrNull() == null || temperatureInput.toDouble() < 0) {
                    if (temperatureInput in exitOptions) {
                        return "Exiting the temperature conversion process."
                    }
                    else {
                        temperatureInput = getUserInput("Please enter a number, ensure the number is above 0 degrees Kelvin: ")
                    }
                }
            }
        }
        val temperatureToConvert: Double = temperatureInput.toDouble() // Converting validated input to Double.
        // Access the correct worded unit from unitsToPresent to pass to conversionService()
        firstUnit = unitsToPresent[firstUnit.toInt() - 1]
        secondUnit = unitsToPresent[secondUnit.toInt() - 1]
        val conversionResult: Double = conversionService(firstUnit, secondUnit, temperatureToConvert)
        /* While the validation ensures passing of no value below absolute zero in any unit, the if statement acts as a redundancy.
        Exits the temperature conversion process in the case of an unexpected input being passed to conversionService().
         */
        if (conversionResult == -999.9) {
            return "Error: the units chosen were not entered correctly. Exiting the temperature conversion process."
        }
        return ("$temperatureToConvert degrees $firstUnit = $conversionResult degrees $secondUnit.")
    }
}