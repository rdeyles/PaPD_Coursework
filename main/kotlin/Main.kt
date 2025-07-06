/**
 * Defines a constant character for a new line.
 * This is used to ensure consistent new line formatting across different output messages.
 */
const val NEW_LINE = '\n'

/**
 * Defines a constant empty string.
 * Useful for initializing string variables or representing an absence of a message.
 */
const val EMPTY_MESSAGE = ""

/**
 * Defines the exit options for the user to type.
 */
val EXIT_OPTIONS: Set<String> = setOf("exit", "end", "cancel", "stop", "quit") // Allow a few likely exit commands.

/**
 * Main function to control which command function is called.
 *
 * User prompt to input a choice between Sum, Factorial or Conversion functions (1, 2 or 3).
 *
 * User input request repeated if not matching one of the options.
 *
 * Prints the output of the chosen command, then returns to command selection or ends process based on user input.
 *
 * E.g.,
 *  *
 *  * user inputs "4" --> "Please type 1, 2, or 3: "
 *  *
 *  * user inputs "sum" --> "Please type 1, 2, or 3."
 *  *
 *  * user inputs "1" --> "The sum of cube numbers up to <n> cubed is ..."
 *  *
 *  * user inputs "3" --> ConversionService called, main() prints "<x> degrees <Fahrenheit/Celsius/Kelvin> is <y> degrees <Fahrenheit/Celsius/Kelvin>."
 *
 * @return Unit
 */
fun main(): Unit {

    // Welcome message printed at the start of the program.
    printMessage("Welcome!", 4, NEW_LINE)
    var endProgram: String = "1" // Controls the main program loop. "1" means continue, "2" means end.
    val endOptions: Set<String> = setOf("1", "2")
    // Main program loop: continues as long as the user wants to return to the main menu.
    while (endProgram == "1") {
        printMainMenu() // Display the main menu options to the user.

        // Get the user's choice for which command to run.
        var choice:String = getUserInput("Please select the command you want to run by typing the number:",4)

        // Validation to ensure only a number from the options is chosen.
        val choices: Set<String> = setOf("1", "2", "3") // Numbers to minimise input requirements.
        while (choice !in choices) {
            choice = getUserInput(
                "Please type either 1, 2 or 3:",4
            )
        }
        var commandResult: String = EMPTY_MESSAGE

        // Execute the chosen command based on user input.
        when (choice) {
            "1" -> {
                // Instantiate and execute the SumCommand.
                val sumCommand = SumCommand()
                commandResult = sumCommand.execute()
            }

            "2" -> {
                // Instantiate and execute the FactorialCommand.
                val factorialCommand: FactorialCommand = FactorialCommand()
                commandResult = factorialCommand.execute()
            }

            "3" -> {
                // Instantiate and execute the ConversionCommand.
                val conversionCommand: ConversionCommand = ConversionCommand()
                commandResult = conversionCommand.execute()
            }
        }
        printResult(commandResult) // Display the result of the executed command.

        // Prompt the user for continuing or ending the program.
        printMessage("Please choose from the following options:",4,NEW_LINE)
        printMessage("1. Main menu",4,NEW_LINE)
        printMessage("2. End program",4,NEW_LINE)
        endProgram = getUserInput(">",4) // Get user's choice for program continuation.


        // Validation loop for program continuation choice.
        while (endProgram !in endOptions) {
            endProgram = getUserInput("Please type 1 or 2:",4)
        }
    }
    // Program ends with a goodbye message when the user chooses "2".
    printMessage("Thank you. Goodbye!",4)
    return
}

/**
 * Prints the main menu options to the console.
 * This function displays a list of available commands that the user can choose from.
 */
fun printMainMenu(){
    println()
    printMessage("Main Menu",4,NEW_LINE)
    println()
    printMessage("You are able to choose one of three commands:",4,NEW_LINE)
    printMessage("1. Computing the sum of cubes of consecutive positive integers up to a provided value.",4,NEW_LINE)
    printMessage("2. Computing the sum of the factorials of three given positive integers.",4,NEW_LINE)
    printMessage("3. Converting a temperature from one unit to another: Celsius, Fahrenheit or Kelvin.",4,NEW_LINE)
}

/**
 * Prints the result of an executed command to the console, followed by an empty line for better readability.
 * @param result The `String` containing the message or output from the executed command.
 */
fun printResult(result:String){
    printMessage(result,4,NEW_LINE)
    println()
}