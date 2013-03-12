
package checkpoint.manager.exceptions;

/**
 * The Class ArguementParseException.
 * Thrown if the command line arguments could not be parsed.
 * @author Samuel Jackson (slj11@aber.ac.uk)
 */
@SuppressWarnings("serial")
public class ArgumentParseException extends Exception{
    
    /* (non-Javadoc)
     * @see java.lang.Throwable#getMessage()
     */
    @Override
    public String getMessage() {
        return "Could not parse command line arguments";
    }
}
