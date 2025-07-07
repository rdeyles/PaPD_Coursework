package ppdcoursework

import java.math.BigInteger


/**
 * The ppdcoursework.FactorialCommand class is designed to calculate the sum of factorials
 * for a set of user-provided non-negative integers.
 * It interacts with the user to obtain input, validates it,
 * computes the factorials using `BigInteger` for large numbers, and then sums them up,
 * returning a formatted string result or an error message.
 */
class FactorialCommand:Command {

    /**
     * Executes the ppdcoursework.main logic of the `ppdcoursework.FactorialCommand`.
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
        printMessage("Please enter three non-negative integers to calculate the sum of their factorials. A valid range is [0,${Int.MAX_VALUE}]",4,NEW_LINE)
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
