import kotlin.String

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
fun getUserInput(message: String): String {
    print(message)
    return readln()
}
