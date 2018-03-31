package es.ucm.fdi.business.ProfileManagement.ManagementTools;

import java.time.LocalDate;
import java.util.regex.Pattern;

/**
 * Class with stati methods used as a tool to check the validity
 * of arguments in management methods.
 */
public class ParsingTool {
    /**
     * A pattern that matches non-alphanumeric sequences [^A-Za-z0-9_].
     * Used for alphanumeric Strings with no whitespaces.
     */
    static Pattern nonAlphaNumChecker = Pattern.compile("\\W+");
    
    /**
     * A pattern that matches ASCII printable chars from Whitespace(#32) to ~(#126). 
     * Not allowed extended ASCII chars (from #126 upwards).
     */
    static Pattern nonPrintableChecker = Pattern.compile("\\[^ -~]");

    /**
     * A pattern that matches alphabet characters. 
     */
    static Pattern alphabetChecker = Pattern.compile("\\[A-Za-z]");

    /**
     * A pattern that matches numeric characters.
     */
    static Pattern numericChecker = Pattern.compile("\\[0-9]");

    /**
     * A pattern that matches an email address elements.
     * Example: ABCdef123.hijKLM@NOPqrs456.Tu
     */
    static Pattern emailChecker = Pattern.compile("\\[A-z0-9._%+-]+[@][A-z0-9-]+[.][A-z0-9.]{2,}");

    /**
     * A pattern that matches a name with at least one surname. No
     * extended ASCII characters allowed.
     */
    static Pattern nameChecker = Pattern.compile("\\[A-z]+([ ][A-z]+){1,}");
    

    /**
     * Parses id so it must be an alphanumerical sequence with no
     * whitespaces
     * 
     * @param id id to be parsed
     * @return if id is valid
     */
    public static boolean parseID(String id) {
        boolean valid = true;
        
        if ( nonAlphaNumChecker.matcher(id).matches() ) {
            // Invalid ID
            valid = false;
        }

        return valid;
    }

    /**
     * Parses username so it must be an alphanumerical sequence with
     * no whitespaces.
     */
    public static boolean parseUsername(String username) {
        boolean valid = true;
        
        if ( nonAlphaNumChecker.matcher(username).matches() ) {
            // Invalid username
            valid = false;
        }

        return valid;
    }

    /**
     * Parses a user's password in the moment of account creation to ensure
     * password security. The password stored in the database will be a hashed key
     * extracted from this password.
     * 
     * @param password user login password
     */
    public static boolean parsePassword(String password) {
        boolean valid = true;

        if (password.length() < 8) {
            // No longer than 8 chars
            valid = false;
        } else if ( ! alphabetChecker.matcher(password).matches() ) {
            // No alphabet chars
            valid = false;
        } else if ( ! numericChecker.matcher(password).matches() ) {
            // No numeric chars
            valid = false;
        } else if ( nonPrintableChecker.matcher(password).matches() ) {
            // Non printable chars
            valid = false;
        }

        return valid;
    }

    /**
     * Parses a user's email to check if it is a valid email format.
     * 
     * @param email user's id
     */
    public static boolean parseEmail(String email) {
        boolean valid = true;

        if ( ! emailChecker.matcher(email).matches() ) {
            valid = false;
        }

        return valid;
    }

    /**
     * Parses a user's name to check if it is valid:
     * alphabetic characters and with surname.
     */
    public static boolean parseName(String name) {
        boolean valid = true;

        if ( ! nameChecker.matcher(name).matches() ) {
            valid = false;
        }

        return valid;
    }

    /**
     * Parses a user birthday's registration to ensure
     * he is +18.
     */
    public static boolean parseBirthday(LocalDate birthday) {
        boolean valid = true;
        
        LocalDate allowedDate = LocalDate.now();
        allowedDate.minusYears(18);

        if ( birthday.isAfter(allowedDate) ) {
            valid = false;
        }

        return valid;
    }
}