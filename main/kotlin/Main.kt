/**
 * Main function to control which command function is called.
 *
 * ## Input
 * User prompt to input a choice between Sum, Factorial or Conversion functions (1, 2 or 3).
 *
 * ## Validation
 * User input request repeated if not matching one of the options.
 *
 * E.g.,
 *  *
 *  * user input == "4" -> "Please type 1, 2, or 3: "
 *  *
 *  * user input == "sum" -> "Please type 1, 2, or 3."
 *  *
 *  * user input == "1" -> "The sum of cube numbers up to <n> cubed is ..."
 *  *
 *  * user input == "3" -> "<x> degrees <Fahrenheit/Celsius/Kelvin> is <y> degrees <Fahrenheit/Celsius/Kelvin>."
 *
 * @return result of the chosen command function.
 */
fun main(): Unit {
    var choice: String = getUserInput("""
        Welcome!
        You are able to choose one of three commands:
        1. Computing the sum of cubes of consecutive positive integers up to a provided value.
        2. Computing the sum of the factorials of three given positive integers.
        3. Converting a temperature from one unit to another: Celcius, Fahrenheit or Kelvin.

        Please select the command you want to run by typing the name as seen above by typing the number: 
        """.trimMargin())
    // Validation to ensure only a number from the options is chosen.
    val choices: Array<String> = arrayOf("1", "2", "3") // Numbers to minimise input requirements.
    while (choice !in choices) {
        choice = getUserInput("""
        Oh, it doesn't look like that was correct.
        Please type either 1, 2 or 3: 
        """)
    }
    var commandResult: String = ""
    when (choice) {
        "1" -> {
            //val sumCommand: Sum()
            //commandResult = sumCommand.completehere
            commandResult = "Sum" // Placeholder to keep the code functional before Guido integrates the Sum class.
        }
        "2" -> {
            //val factorialCommand: Factorial = Factorial()
            //commandResult = factorialCommand.completehere
            commandResult = "Factorial" // Placeholder to keep the code functional before Guido integrates the Factorial class.
        }
        "3" -> {
            val conversionCommand: ConversionService = ConversionService()
            commandResult = conversionCommand.temperatureConversion()
        }
    }
    print(commandResult)
}
