package frontEnd.ReadWrite;


import javax.xml.parsers.ParserConfigurationException;

/**
 * This class represents what might go wrong when using XML files.
 *
 * @author Robert C. Duvall
 */
public class WriteException extends RuntimeException{
    // for serialization
    private static final long serialVersionUID = 1L;


    /**
     * Create an exception based on an issue in our code.
     */
    public WriteException(String message, Object ... values) {
        super(String.format(message, values));
    }

    /**
     * Create an exception based on a caught exception with a different message.
     */
    public WriteException(Throwable cause, String message, Object ... values) {
        super(String.format(message, values), cause);
    }

    /**
     * Create an exception based on a caught exception, with no additional message.
     */
    public WriteException(Throwable cause) {
        super(cause);
    }
}
