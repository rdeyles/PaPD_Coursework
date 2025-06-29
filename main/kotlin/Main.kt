import kotlin.math.round

/**
 * Reusable function to display a message to the user to prompt an input.
 *
 * ## Examples:
 *
 * getUserInput("Type the number 3 here. ") expects "3".
 *
 * getUserInput("Type the number 3 here. ") could return "Hello".
 *
 * @param message clear and concise to prompt the intended response.
 *
 * @return readln() user input, input validation will be handled by the command functions.
 */
fun getUserInput(message: String): String {
    print(message)
    return readln()
}

/**
 * Converts between two temperature units based on user input. Validates input and reprompts until the input is the correct format.
 *
 * ## Preconditions:
 *
 * In the unlikely case of attempting to convert an extremely large temperature, input values exceeding 1,193,946,452 may return inaccurate conversions.
 *
 * User input, no parameters.
 * ## Examples:
 *
 * User inputs request 125 degrees Celsius to Kelvin --> "125 degrees Celsius = 398.15 degrees Kelvin."
 *
 * User inputs request -125 degrees Kelvin to Fahrenheit --> "Please enter a number, remember Kelvin starts at 0: "
 *
 * @return String -- either "Exit" when user decides to terminate the process early, or the results of the conversion.
 */

fun conversion(): String {
    val units: Array<String> = arrayOf("Celsius", "Fahrenheit", "Kelvin", "Exit")
    println("You are able to convert between Celsius, Fahrenheit and Kelvin.")
    var confirm: String = ""
    var firstUnit: String = ""
    var secondUnit: String = ""
    while (confirm != "Y") {
        firstUnit = getUserInput("Please enter which unit you wish to convert from (type Exit if you wish to go back to the main menu): ")
        while (firstUnit !in units) {
            getUserInput("That is not one of the choices, please type either Celsius, Fahrenheit, Kelvin or Exit if you want to go back.")
        }
        if (firstUnit == "Exit") {
            return "Exit"
        }
        secondUnit = getUserInput("Please enter which unit you wish to convert to (type Exit if you wish to go back to the main menu): ")
        while (firstUnit !in units) {
            getUserInput("That is not one of the choices, please type either Celsius, Fahrenheit, Kelvin or Exit if you want to go back.")
        }
        if (secondUnit == "Exit") {
            return "Exit"
        }
        println("You have selected to convert ${firstUnit} to ${secondUnit}.")
        confirm = getUserInput("Please confirm if this is correct (Y/N): ")
        while (confirm != "Y" && confirm != "N") { // Avoiding any inputs other than Y or N
            confirm = getUserInput("Please enter Y for yes or N for no: ")
        }
    }
    var valueInput: String = getUserInput("Please enter the temperature to convert: ")
    if (firstUnit == "Celsius" && secondUnit == "Kelvin") {
        while (valueInput.toDoubleOrNull() == null || valueInput.toDouble() < -273.15) {
            valueInput = getUserInput("-273.15 degrees Celsius is the coldest possible temperature. Please enter a larger number: ")
        }
    }
    when (firstUnit) {
        "Fahrenheit" if secondUnit == "Kelvin" -> {
            while (valueInput.toDoubleOrNull() == null || valueInput.toDouble() < -459.67) {
                valueInput = getUserInput("-273.15 degrees Celsius is the coldest possible temperature. Please enter a larger number: ")
            }
        }
        "Celsius", "Fahrenheit" -> {
            while (valueInput.toDoubleOrNull() == null) {
                valueInput = getUserInput("Please enter a number: ")
            }
        }
        "Kelvin" -> {
            while (valueInput.toDoubleOrNull() == null || valueInput.toDouble() < 0) {
                valueInput = getUserInput("Please enter a number, remember Kelvin starts at 0: ")
            }
        }
    }
    val valueToConvert: Double = valueInput.toDouble()
    var valueConverted: Double = 0.0
    when (firstUnit) {
        "Celsius" if secondUnit == "Fahrenheit" -> {
            valueConverted = valueToConvert * 9 / 5 + 32
        }
        "Fahrenheit" if secondUnit == "Celsius" -> {
            valueConverted = (valueToConvert - 32) * 5 / 9
        }
        "Celsius" if secondUnit == "Kelvin" -> {
            valueConverted = valueToConvert + 273.15
        }
        "Kelvin" if secondUnit == "Celsius" -> {
            valueConverted = valueToConvert - 273.15
        }
        "Fahrenheit" if secondUnit == "Kelvin" -> {
            valueConverted = (valueToConvert - 32) * 5 / 9 + 273.15
        }
        "Kelvin" if secondUnit == "Fahrenheit" -> {
            valueConverted = (valueToConvert - 273.15) * 9 / 5 + 32
        }
    }
    valueConverted = round(valueConverted * 100.0) / 100.0
    return ("${valueToConvert} degrees ${firstUnit} = ${valueConverted} degrees ${secondUnit}.")
}

/**
 * Main function to control which command function is called.
 *
 * ## Input
 * User prompt to input a choice between Sum, Conversion or Factorial functions.
 *
 * ## Validation
 * User input request repeated if not matching one of the keywords.
 * @return result of the chosen command function.
 * E.g.,
 *
 * user input == 4 -> "Please type either Sum, Factorial or Conversion."
 *
 * user input == sum -> "Please type either Sum, Factorial or Conversion."
 *
 * user input == Sum -> "The sum of cube numbers up to <n> cubed is ..."
 *
 * user input == Conversion -> "<x> degrees <Fahrenheit/Celsius/Kelvin> is <y> degrees <Fahrenheit/Celsius/Kelvin>."
 */
fun main(): Unit {
    var choice: String = getUserInput("""
        Welcome!
        You are able to choose one of three commands:
        Sum: Computes the sum of cubes of consecutive integers.
        Factorial: Computes the sum of the factorials of three given integers.
        Conversion: Converts a temperature from one unit to another: Celcius, Fahrenheit or Kelvin.

        Please select the command you want to run by typing the name as seen above.
        """)
    val choices: Array<String> = arrayOf("Sum", "Factorial", "Conversion")
    while (choice !in choices) {
        choice = getUserInput("""
        Oh, it doesn't look like that was correct.
        Please type either Sum, Factorial or Conversion exactly as written here.
        """)
    }
    var commandResult: String = ""
    when (choice) {
        "Sum" -> {
            commandResult = "sum"
        }
        "Factorial" -> {
            commandResult = "factorial"
        }
        "Conversion" -> {
            commandResult = conversion()
        }
    }
    print(commandResult)
}
