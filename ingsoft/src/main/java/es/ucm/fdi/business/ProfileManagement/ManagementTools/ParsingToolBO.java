package es.ucm.fdi.business.ProfileManagement.ManagementTools;

import java.time.LocalDate;
import java.util.Collection;
import java.util.regex.Pattern;

/**
 * Class with stati methods used as a tool to check the validity
 * of arguments in management methods.
 */
public class ParsingToolBO {
    /**
     * A pattern that matches alphanumeric sequences [A-Za-z0-9_].
     * Used for alphanumeric Strings with no whitespaces.
     * 
     * @see #parseID(String)
     * @see #parseUsername(String)
     */
    public static Pattern alphaNumKeyChecker = Pattern.compile("\\^(\\w+)$");
    
    /**
     * A series of Patterns to match a password that matches ASCII 
     * printable sequence from Whitespace(#32) to ~(#126) and size over 8. 
     * At least minus, capital and numeric char. 
     * Not allowed extended ASCII chars (from #126 upwards).
     * 
     * @see #parsePassword(String)
     */
    private static Pattern[] passwordCheckers = new Pattern[4];
    {
            passwordCheckers[0] = Pattern.compile("\\^([ -~]{8,})$");
            passwordCheckers[1] = Pattern.compile("\\.*[A-Z].*");
            passwordCheckers[2] = Pattern.compile("\\.*[a-z].*");
            passwordCheckers[3] = Pattern.compile("\\.*[0-9].*");
    }

    /**
     * A pattern that matches an email address elements.
     * Example: ABCdef123.hijKLM@NOPqrs456.ucm.es
     * 
     * @see #parseEmail(String)
     */
    public static Pattern emailChecker = Pattern.compile("\\^([A-z0-9._%+-]+)[@]([A-z0-9-]+[.][A-z0-9.]{2,})$");

    /**
     * A pattern that matches a name with at least one surname. No
     * extended ASCII characters allowed.
     * 
     * @see #parseName(String)
     */
    public static Pattern nameChecker = Pattern.compile("\\^(?:\b[A-z]+\b[ \r\n]*){2,}$");
    
    /**
     * A pattern that matches an address. Typically used address chars
     * allowed.
     */
    public static Pattern addressChecker = Pattern.compile("\\^[A-z0-9\\/ªº ]+$");

    /**
     * A pattern that matches a valid tag: #-preceded and 
     * less than 25 chars long. It also groups the tag without #.
     */
    public static Pattern tagChecker = Pattern.compile("\\^[#]([A-z0-9_]+)$");

    /**
     * A pattern that matches a valid opinion: printable characters and
     * less than 280 chars long.
     */
    public static Pattern opinionChecker = Pattern.compile("\\^(?:[ -~\n\r]{1,280})$");

    /**
     * Parses id so it must be an alphanumerical sequence with no
     * whitespaces
     * 
     * @param id id to be parsed
     * @return if id is valid
     */
    public static boolean parseID(String id) {
        boolean valid = true;
        
        if ( ! alphaNumKeyChecker.matcher(id).matches() ) {
            // Invalid ID
            valid = false;
        }

        return valid;
    }

    /**
     * Parses username so it must be an alphanumerical sequence with
     * no whitespaces.
     * 
     * @param username username to be parsed
     * @return if username is valid
     */
    public static boolean parseUsername(String username) {
        boolean valid = true;
        
        if ( ! alphaNumKeyChecker.matcher(username).matches() ) {
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
     * @param password user login password to be parsed
     * @return if password is valid
     */
    public static boolean parsePassword(String password) {
        boolean valid = true;

        for (Pattern p : passwordCheckers) {
            if ( ! p.matcher(password).matches() ) {
                valid = false;
            }
        }

        return valid;
    }

    /**
     * Parses a user's email to check if it is a valid email format.
     * 
     * @param email email to be parsed
     * @return if email is valid
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
     * 
     * @param name user name to be parsed
     * @return if name is valid
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
     * 
     * @param birthday birthday to be parsed
     * @return if birthday is valid
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

    /**
     * Parses a club commercial name. Initially no conditions.
     * 
     * @param commName commercial name to be parsed
     * @return if commercial name is valid
     */
    public static boolean parseCommercialName(String commName) {
        return true;
    }

    /** 
     * Parses a club address, with an address typically used symbols.
     * 
     * @param address address to be parsed
     * @return if address is valid
     */
    public static boolean parseAddress(String address) {
        boolean valid = true;

        if ( ! addressChecker.matcher(address).matches() ) {
            valid = false;
        }

        return valid;
    }

    /**
     * Parses a club ticket price to ensure it is not a negative
     * value.
     * 
     * @param price price to be parsed
     * @return if price is valid
     */
    public static boolean parsePrice(float price) {
        boolean valid = true;

        if (price < 0.0f) {
            valid = false;
        }

        return valid;
    }

    /**
     * Parses a tag collection to ensure every tag is correct.
     * 
     * @param tags <code>Collection</code> of <code>String</code>
     * @return if tags are valid
     */
    public static boolean parseTags(Collection<String> tags) {
        boolean valid = true;

        for (String tag : tags) {
            if ( ! tagChecker.matcher(tag).matches() ) {
                valid = false;
            }
        }

        return valid;
    }

    /**
     * Parses a user rating to ensure it is an <code>Integer</code> between
     * 0 and 10.
     * 
     * @param rate user rating
     * @return if rate is valid
     */
    public static boolean parseRate(int rate) {
        boolean valid = true;

        if (rate < 0 || 10 < rate) {
            valid = false;
        }

        return valid;
    }

    /**
     * Parses a club rating to ensure it is a <code>Float</code> between
     * 0 and 10.
     */
    public static boolean parseRating(Float rating) {
        boolean valid = true;

        if (rating < 0.0F || 10.0F < rating) {
            valid = false;
        }

        return valid;
    }

    /**
     * Parses a user opinion to ensure it consists of printable
     * characters and is less than 280 chars long.
     */
    public static boolean parseOpinion(String opinion) {
        boolean valid = true;

        if ( ! opinionChecker.matcher(opinion).matches() ) {
            valid = false;
        }

        return valid;
    }
}