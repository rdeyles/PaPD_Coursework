package ppdcoursework

/**
 * The ppdcoursework.SumCommand class is designed to calculate the sum of cubes of the first 'n' natural numbers,
 *  where 'n' is a positive natural number provided by the user.
 *  It handles user input,
 *  validates the input within a specified range, and returns the calculated sum as a string,
 *  or an error message if the input is invalid.
 */
class SumCommand:Command {

    /**
     * Executes the ppdcoursework.main logic of the ppdcoursework.SumCommand class.
     * This function prompts the user to enter a positive natural number, validates the input,
     * calculates the sum of the cubes of natural numbers up to the entered number,
     * and returns the result as a string. It includes robust error handling for invalid input.
     *
     * @return A `String` representing the calculated sum of cubes, or an error message if input is invalid.
     * - The calculated sum as a string if successful.
     * - "[input] is not a valid natural number." if a `NumberFormatException` occurs during input parsing.
     * - "java.lang.IllegalArgumentException: [message]" if an `IllegalArgumentException` occurs due to an out-of-range number.
     */
    override fun execute():String {
        var result = ""
        var input = ""
        while (result.isEmpty()){
            try {
                input = getUserInput("Please enter a positive natural number between [1,92681]:",4)
                if(isExitCommand(input)){
                    return "Exiting the sum of cubes calculation process."
                }
                // Convert the input string to an Int
                // Validate that the number is within the acceptable range [1, 92681].
                // If not, it throws an IllegalArgumentException.
                val n = validate(input)
                // If validation passes, calculate the sum of cubes and return it as a string.
                result = "The sum of the cubes of natural numbers up to $n cubed is ${makeSum(n).toString()}"
            }catch(e:NumberFormatException){
                // Catches error if the input string cannot be converted to a ULong.
                printMessage("$input is not a valid natural number.",4,NEW_LINE)
            }catch(e:IllegalArgumentException){
                // Catches error if the number is outside the required range as per the 'require' check.
                printMessage("$input is not a valid natural number in the range [1,92681].",4,NEW_LINE)
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