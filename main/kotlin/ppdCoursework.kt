import java.math.BigInteger
import kotlin.math.round

/**
 * The `Command` interface is a fundamental component for implementing the **Command design pattern**.
 * This pattern helps decouple the sender of a request from the receiver,
 * allowing one to encapsulate a request as an object.
 *
 * The primary purpose of the `Command` interface is to define a contract for **executable operations**.
 * Any class that implements this interface provides an `execute()` method.
 * This enables the uniform treatment of different operations, regardless of their specific implementation details.
 */
interface Command {
    /**
     * This abstract function is the core of the Command interface.
     * It represents the action or operation that a concrete command class will perform.
     * The execute() method returns a String, which typically represents the result of the command's execution,
     * a status message, or an error description.
     * @return The result, a status message, or an error description.
     */
    fun execute():String
}

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
 * @return readln() user input, input validation will be handled by the command classes.
 */

fun getUserInput(message:String, tabulations:Int = 0, endLine:Char = ' '  ): String {
    printMessage(message,tabulations,endLine)
    return readln()
}

/**
 * The printMessage function displays a message on the console.
 * The non-negative [tabs] parameter specifies the number
 * of tabs used to indent the message; the default is zero tabs.
 * There is also an optional end-of-line character parameter, which defaults to a whitespace.
 * @param message the required parameter to prompt the intended response.
 * @param tabs optional parameter which specifies the number of tabs used to indent the message, default is zero.
 * @param endLine default is a single space character
 *
 */
fun printMessage(message:String, tabs:Int = 0, endLine:Char = ' '  ){
    print("${"\t".repeat(tabs)}${message}${endLine}")
}

/**
 * The isExitCommand function checks if the [input] parameter is an exit command typed by the user.
 * The input string is trimmed and converted to lowercase.
 * @param input the entered input
 * @return Boolean true if input is defined
 */
fun isExitCommand(input:String):Boolean {
    return input.trimMargin().lowercase() in EXIT_OPTIONS
}

/**
 * The SumCommand class is designed to calculate the sum of cubes of the first 'n' natural numbers,
 *  where 'n' is a positive natural number provided by the user.
 *  It handles user input,
 *  validates the input within a specified range, and returns the calculated sum as a string,
 *  or an error message if the input is invalid.
 */
class SumCommand: Command {

    /**
     * Executes the main logic of the SumCommand class.
     * This function prompts the user to enter a positive natural number, validates the input,
     * calculates the sum of the cubes of natural numbers up to the entered number,
     * and returns the result as a string. It includes robust error handling for invalid input.
     *
     * @return A `String` representing the calculated sum of cubes, or an error message if input is invalid.
     * - The calculated sum as a string if successful.
     * - "[input] is not a valid natural number." if a `NumberFormatException` occurs during input parsing.
     * - "java.lang.IllegalArgumentException: [message]" if an `IllegalArgumentException` occurs due to an out-of-range number.
     */
    override fun execute(): String {
        var result = ""
        var input = ""
        while (result.isEmpty()) {
            try {
                input = getUserInput("Please enter a positive natural number between [1,92681]:", 4)
                if (isExitCommand(input)) {
                    return "Exiting the sum of cubes calculation process."
                }
                // Convert the input string to an Int
                // Validate that the number is within the acceptable range [1, 92681].
                // If not, it throws an IllegalArgumentException.
                val n = validate(input)
                // If validation passes, calculate the sum of cubes and return it as a string.
                result = "The sum of the cubes of natural numbers up to $n cubed is ${makeSum(n).toString()}"
            } catch (e: NumberFormatException) {
                // Catches error if the input string cannot be converted to a ULong.
                printMessage("$input is not a valid natural number.", 4, NEW_LINE)
            } catch (e: IllegalArgumentException) {
                // Catches error if the number is outside the required range as per the 'require' check.
                printMessage("$input is not a valid natural number in the range [1,92681].", 4, NEW_LINE)
            }
        }
        return result
    }

    /**
     * Validates a given input string to ensure it represents a non-negative integer
     * within the range of 1 to 92681 (inclusive).
     *
     * @param input The `String` provided by the user to be validated.
     * @return The validated integer if it meets the criteria.
     * @throws NumberFormatException If the input string cannot be parsed as an integer.
     * @throws IllegalArgumentException If the parsed integer is less than 1 or greater than 92681.
     *
     */
    private fun validate(input: String): Int {
        val a = input.toInt() // Converts the input string to an integer. Throws NumberFormatException if invalid.
        require(a in 1..92681) {
            // Checks if the number is within the valid range. Throws IllegalArgumentException if not.
            "A valid number must be between 1 and 92681 inclusive."
        }
        return a
    }

    /**
     * Calculates the sum of the cubes of the first 'n' natural numbers using the direct formula.
     *
     * This function efficiently computes the sum of cubes without needing to iterate or
     * calculate individual cubes.
     *
     * @param n The positive natural number (as an Int) up to which the sum of cubes is to be calculated.
     * @return A `ULong` representing the sum of the cubes of the first 'n' natural numbers.
     */
    fun makeSum(n: Int): ULong {
        // Calculate the sum of the first 'n' natural numbers: (n * (n + 1)) / 2.
        // This intermediate result is called a triangular number.
        val tmp: ULong = ((n.toULong() + 1u) * n.toULong()) / 2u
        // Square the triangular number to get the sum of cubes.
        return tmp * tmp
    }
}

/**
 * The FactorialCommand class is designed to calculate the sum of factorials
 * for a set of user-provided non-negative integers.
 * It interacts with the user to obtain input, validates it,
 * computes the factorials using `BigInteger` for large numbers, and then sums them up,
 * returning a formatted string result or an error message.
 */
class FactorialCommand: Command {

    /**
     * Executes the main logic of the `FactorialCommand`.
     * This function prompts the user to enter three non-negative integers, validates each input,
     * calculates the sum of their factorials, and returns a formatted string with the result.
     * It handles invalid input gracefully by returning an appropriate error message.
     *
     * @return A `String` representing the sum of the factorials, or an error message if input is invalid.
     * - "The sum of the factorials is [result]" if successful.
     * - "[input] is not a valid natural number." if a `NumberFormatException` occurs during input parsing.
     * - "java.lang.IllegalArgumentException: [message]" if an `IllegalArgumentException` occurs during validation.
     */
    override fun execute(): String {
        val args = ArrayList<Int>()
        printMessage(
            "Please enter three non-negative integers to calculate the sum of their factorials. A valid range is [0,${Int.MAX_VALUE}]",
            4,
            NEW_LINE
        )
        printMessage("Large numbers may take a significant amount of time depending on your machine.",4,NEW_LINE)
        var index = 0
        while(args.size < 3){ // Loop three times to get three numbers from the user.
            var input = ""
            try {

                input = getUserInput("Integer #${index + 1}: ", 4)
                if(isExitCommand(input)){
                    return "Exiting the factorial calculation process."
                }
                val a = validate(input) // Validate the user's input.
                args.add(a) // Add the validated integer to the list.
                index += 1
            } catch (e: NumberFormatException) {
                // Catches error if the input string cannot be converted to an integer.
                printMessage("$input is not a valid natural number in the range [0,${Int.MAX_VALUE}].",4,NEW_LINE)
            } catch (e: IllegalArgumentException) {
                // Catches error if the input integer is out of the specified range (0 to Int.MAX_VALUE).
                printMessage("$input is not a valid natural number in the range [0,${Int.MAX_VALUE}].",4,NEW_LINE)
            }
        }

        // If all inputs are valid, calculate the sum of factorials and return the result.
        val sum = makeFactorial(args).toString()
        if(sum.length > 1024){
            return "The sum of the factorials ${args.joinToString(separator = "!, ", postfix = "!")} is a number with ${sum.length} digits and these are the first 1024 digits: ${sum.substring(0,1024)}..."
        }
        return "The sum of the factorials ${args.joinToString(separator = "!, ", postfix = "!")} is a number with ${sum.length} digits and is $sum"
    }

    /**
     * Calculates the sum of factorials for a given list of integers.
     * It iterates through each integer in the list, computes its factorial using `computeFactorial`,
     * and accumulates the results into a `BigInteger` sum.
     *
     * @param args A `List<Int>` containing the integers for which to calculate the sum of factorials.
     * @return A `BigInteger` representing the total sum of the factorials.
     */
    fun makeFactorial(args: List<Int>): BigInteger {
        var sum = BigInteger.ZERO // Initialize the sum to zero.
        for (arg in args) {
            //val time = measureTime {
            //println("Computing factorial of $arg")
            // For each integer in the list, compute its factorial and add to the result.
            sum += computeFactorial(arg.toBigInteger())
            //println("Finished computing factorial of $arg")
            //}
            //println("Time $time")
        }
        return sum
    }

    /**
     * Validates a given input string to ensure it represents a non-negative integer
     * within the range of 0 to `Int.MAX_VALUE` (inclusive).
     *
     * @param input The `String` provided by the user to be validated.
     * @return The validated integer if it meets the criteria.
     * @throws NumberFormatException If the input string cannot be parsed as an integer.
     * @throws IllegalArgumentException If the parsed integer is less than 0 or greater than `Int.MAX_VALUE`.
     *
     */
    private fun validate(input: String): Int {
        val a = input.toInt() // Converts the input string to an integer. Throws NumberFormatException if invalid.
        require(a in 0 .. Int.MAX_VALUE) { "$input is not a valid natural number in the range [0,${Int.MAX_VALUE}]" }
        return a
    }


    /**
     * Computes the factorial of a given non-negative `BigInteger`.
     * This private helper function handles the calculation iteratively.
     *
     * @param n The `BigInteger` for which to calculate the factorial. Must be non-negative.
     * @return A `BigInteger` representing the factorial of `n`.
     * - Returns `BigInteger.ONE` if `n` is 0 or 1.
     * - Returns the product of all positive integers up to `n` for `n > 1`.
     */
    private fun computeFactorial(n: BigInteger): BigInteger {
        // Base cases for factorial calculation.
        if (n == BigInteger.ZERO || n == BigInteger.ONE) {
            return BigInteger.ONE
        }
        var index: BigInteger = BigInteger.ONE
        var result: BigInteger = BigInteger.ONE
        // Iteratively multiplies `result` by numbers from 2 up to `n`.
        while (index < n) {
            result *= (index + BigInteger.ONE)
            index += BigInteger.ONE
        }
        return result
    }
}

/**
 * The ConversionCommand class is designed to convert between two units of temperature.
 *
 * It interacts with the user to obtain input, validates it,
 * converting between the units and provided temperature value,
 * returning a formatted string result or an exit message.
 */
class ConversionCommand: Command {
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
        printMessage("Please choose one of the following:", 4, NEW_LINE)
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
        return (resultToString(unitChoice, tempToConvert, tempResult)) // Return the computation result as a string message.
    }

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
}

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