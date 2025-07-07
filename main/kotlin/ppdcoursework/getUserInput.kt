package ppdcoursework

import kotlin.String

/**
 * Reusable function to display a message to the user to prompt an input.
 *
 * ## Examples:
 *
 * ppdcoursework.getUserInput("Type the number 3 here. ") expects "3".
 *
 * ppdcoursework.getUserInput("Type the number 3 here. ") could return "Hello".
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
 * The ppdcoursework.printMessage function displays a message on the console.
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
 * The ppdcoursework.isExitCommand function checks if the [input] parameter is an exit command typed by the user.
 * The input string is trimmed and converted to lowercase.
 * @param input the entered input
 * @return Boolean true if input is defined
 */
fun isExitCommand(input:String):Boolean {
    return input.trimMargin().lowercase() in EXIT_OPTIONS
}
