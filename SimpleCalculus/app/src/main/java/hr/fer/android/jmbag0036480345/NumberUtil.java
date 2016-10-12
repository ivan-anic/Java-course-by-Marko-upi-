package hr.fer.android.jmbag0036480345;

/**
 * A utility class which offers methods which help working with numbers.
 *
 * @author Ivan Anić
 * @version 1.0
 */
public class NumberUtil {

    /**
     * Parses a number from the given text.
     *
     * @param text - the textual representation of the given number
     * @return the desired number as a double, or null if a parsing exception occurred
     */
    protected static Double ParseDouble(String text){
        Double ret;
        try {
            ret = Double.parseDouble(text);
        } catch (NumberFormatException ex){
            return null;
        }
        return ret;
    }

}
