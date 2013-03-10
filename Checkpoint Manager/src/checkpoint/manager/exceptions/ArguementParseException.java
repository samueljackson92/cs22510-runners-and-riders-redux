/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package checkpoint.manager.exceptions;

/**
 *
 * @author samuel
 */
public class ArguementParseException extends Exception{
    @Override
    public String getMessage() {
        return "Could not parse command line arguments";
    }
}
