/**
 * The SumCommand class is designed to calculate the sum of cubes of the first 'n' natural numbers,
 *  where 'n' is a positive natural number provided by the user.
 *  It handles user input,
 *  validates the input within a specified range, and returns the calculated sum as a string,
 *  or an error message if the input is invalid.
 */
class SumCommand:Command {

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
    override fun execute():String {
        val input: String = getUserInput("Please enter a positive natural number between [1,92681]:",4)
        return try {
            // Convert the input string to an Unsigned Long (ULong)
            val n = input.toULong()
            // Validate that the number is within the acceptable range [1, 92681].
            // If not, it throws an IllegalArgumentException.
            require(n >= 1u && n <= 92681u){
                "The number must be between 1 and 92681 inclusive."
            }
            // If validation passes, calculate the sum of cubes and return it as a string.
            "The sum of the cubes of natural numbers up to $n is ${makeSum(n).toString()}"
        }catch(e:NumberFormatException){
            // Catches error if the input string cannot be converted to a ULong.
            ("$input is not a valid natural number.")
        }catch(e:IllegalArgumentException){
            // Catches error if the number is outside the required range as per the 'require' check.
            "$e.toString()"
        }
    }

    /**
     * Calculates the sum of the cubes of the first 'n' natural numbers using the direct formula.
     *
     * This function efficiently computes the sum of cubes without needing to iterate or
     * calculate individual cubes.
     *
     * @param n The positive natural number (as a ULong) up to which the sum of cubes is to be calculated.
     * @return A `ULong` representing the sum of the cubes of the first 'n' natural numbers.
     */
    private fun makeSum(n: ULong): ULong {
        // Calculate the sum of the first 'n' natural numbers: (n * (n + 1)) / 2.
        // This intermediate result is often called a triangular number.
        val tmp: ULong = ((n + 1u) * n) / 2u
        // Square the triangular number to get the sum of cubes.
        return tmp * tmp
    }
}
