package ppdcoursework

/**
 * The `ppdcoursework.Command` interface is a fundamental component for implementing the **ppdcoursework.Command design pattern**.
 * This pattern helps decouple the sender of a request from the receiver,
 * allowing one to encapsulate a request as an object.
 *
 * The primary purpose of the `ppdcoursework.Command` interface is to define a contract for **executable operations**.
 * Any class that implements this interface provides an `execute()` method.
 * This enables the uniform treatment of different operations, regardless of their specific implementation details.
  */
interface Command {
    /**
     * This abstract function is the core of the ppdcoursework.Command interface.
     * It represents the action or operation that a concrete command class will perform.
     * The execute() method returns a String, which typically represents the result of the command's execution,
     * a status message, or an error description.
     * @return The result, a status message, or an error description.
     */
    fun execute():String
}