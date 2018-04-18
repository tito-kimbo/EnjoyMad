package es.ucm.fdi.business.util;

import java.time.LocalDate;
import java.util.Collection;
import java.util.regex.Pattern;

/**
 * Class with static methods used as a tool to check the validity
 * of arguments, mainly in ProfileManagement methods.
 */
public class ParsingToolBO {
    /**
     * A <code>Pattern</code> that matches alphanumeric sequences [A-Za-z0-9_].
     * Used for alphanumeric <code>Strings</code> with no whitespaces.
     * 
     * @see #parseID(String)
     * @see #parseUsername(String)
     */
    public static Pattern alphaNumKeyChecker = Pattern.compile("\\^(\\w+)$");
    
    /**
     * <p>
     * A series of <code>Patterns</code> to match a password that matches ASCII 
     * printable sequence from <code>Whitespace(#32)</code> to <code>~(#126)</code> 
     * and size over <code>8</code>. 
     * </p> <p>
     * At least minus, mayus and numeric char.
     * No extended <code>ASCII</code> characters allowed 
     * (from <code>#126</code> upwards).
     * </p>
     * 
     * @see #parsePassword(String)
     */
    private static Pattern[] passwordCheckers = new Pattern[4];
    {
            passwordCheckers[0] = Pattern.compile("\\^([ -~]{8,})$"); // Valid chars (at least 8)
            passwordCheckers[1] = Pattern.compile("\\.*[A-Z].*"); // At least a mayus letter
            passwordCheckers[2] = Pattern.compile("\\.*[a-z].*"); // At least a minus letter
            passwordCheckers[3] = Pattern.compile("\\.*[0-9].*"); // At least a number
    }

    /**
     * <p>
     * A <code>Pattern</code> that matches an <code>Email Address</code>.
     * </p> <p>
     * Example:
     * </p> <p> 
     * ABCdef123.hijKLM@NOPqrs456.ucm.es
     * </p>
     * 
     * @see #parseEmail(String)
     */
    public static Pattern emailChecker = Pattern.compile("\\^([A-z0-9._%+-]+)[@]([A-z0-9-]+[.][A-z0-9.]{2,})$");

    /**
     * A <code>Pattern</code> that matches a <code>Name</code> with at 
     * least one surname. No extended <code>ASCII</code> characters allowed.
     * 
     * @see #parseName(String)
     */
    public static Pattern nameChecker = Pattern.compile("\\^(?:\b[A-z]+\b[ \r\n]*){2,}$");
    
    /**
     * A <code>Pattern</code> that matches an <code>Address</code>>. Characters typically used 
     * in an address allowed.
     */
    public static Pattern addressChecker = Pattern.compile("\\^[A-z0-9\\/ªº ]+$");

    /**
     * <p>
     * A <code>Pattern</code> that matches a valid <code>Tag</code>: <code>#</code>-preceded and 
     * less than <code>25</code> chars long.
     * </p> <p> 
     * The <code>Pattern</code> groups the <code>Tag</code> without the <code>#</code>.
     */
    public static Pattern tagChecker = Pattern.compile("\\^[#]([A-z0-9_]+)$");

    /**
     * A <code>Pattern</code> that matches a valid <code>Opinion</code>: printable characters
     * and less than <code>280</code> characters long.
     */
    public static Pattern opinionChecker = Pattern.compile("\\^(?:[ -~\n\r]{1,280})$");

    /**
     * Parses <code>ID</code> so it must be an alphanumerical sequence with no
     * whitespaces.
     * 
     * @param id <code>String</code> with <code>ID</code> to be parsed
     * @return if <code>ID</code> is valid
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
     * Parses <code>Username</code> so it must be an alphanumerical sequence with
     * no whitespaces.
     * 
     * @param username <code>String</code> with <code>Username</code> to be parsed
     * @return if <code>Username</code> is valid
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
     * Parses a user's <code>Login Password</code> in the moment of account creation to ensure
     * password security. The <code>Password</code> stored in the database will be a hashed key
     * extracted from this <code>Login Password</code>.
     * 
     * @param password <code>String</code> with user <code>Login Password</code> to be parsed
     * @return if <code>Login Password</code> is valid
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
     * Parses a user's <code>Email</code> to check if it is a valid format.
     * 
     * @param email <code>String</code> with <code>Email</code> to be parsed
     * @return if <code>email</code> is valid
     */
    public static boolean parseEmail(String email) {
        boolean valid = true;

        if ( ! emailChecker.matcher(email).matches() ) {
            valid = false;
        }

        return valid;
    }

    /**
     * Parses a user's <code>Name</code> to check if it is valid:
     * alphabetic characters and with surname.
     * 
     * @param name <code>String</code> with user <code>Name</code> to be parsed
     * @return if <code>name</code> is valid
     */
    public static boolean parseName(String name) {
        boolean valid = true;

        if ( ! nameChecker.matcher(name).matches() ) {
            valid = false;
        }

        return valid;
    }

    /**
     * Parses a user's <code>Birthday</code> registration to ensure
     * he is +18.
     * 
     * @param birthday <code>LocalDate</code> with <code>Birthday</code> to be parsed
     * @return if <code>birthday</code> is valid
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
     * Parses a club <code>Commercial Name</code>. Initially no conditions.
     * 
     * @param commName <code>String</code> with <code>Commercial Name</code> to be parsed
     * @return if <code>commName</code> is valid
     */
    public static boolean parseCommercialName(String commName) {
        return true;
    }

    /** 
     * Parses a club <code>Address</code>, with the typical used characters in an address.
     * 
     * @param address <code>String</code> with <code>Address</code> to be parsed
     * @return if <code>address</code> is valid
     */
    public static boolean parseAddress(String address) {
        boolean valid = true;

        if ( ! addressChecker.matcher(address).matches() ) {
            valid = false;
        }

        return valid;
    }

    /**
     * Parses a club <code>TicketPrice</code> to ensure it is not a negative
     * value.
     * 
     * @param price <code>Floar</code> with <code>TicketPrice</code> to be parsed
     * @return if <code>price</code> is valid
     */
    public static boolean parsePrice(float price) {
        boolean valid = true;

        if (price < 0.0f) {
            valid = false;
        }

        return valid;
    }

    /**
     * Parses a <code>Tag</code> collection to ensure every <code>Tag</code> is correct.
     * 
     * @param tags <code>Collection<Strings></code> with <code>Tags</code> to be parsed
     * @return if <code>tags</code> are valid
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
     * Parses a user <code>Rate</code> to ensure it is an <code>Integer</code> 
     * between <code>0-10</code>.
     * 
     * @param rate <code>Integer</code> with user <code>Rating</code> to be parsed
     * @return if <code>rate</code> is valid
     */
    public static boolean parseRate(int rate) {
        boolean valid = true;

        if (rate < 0 || 10 < rate) {
            valid = false;
        }

        return valid;
    }

    /**
     * Parses a club <code>Rating</code> to ensure it is a <code>Float</code> between
     * <code>0-10</code>.
     * 
     * @param rating <code>Float</code> with <code>Rating</code> to be parsed
     * @return if <code>rating</code> is between bounds
     */
    public static boolean parseRating(Float rating) {
        boolean valid = true;

        if (rating < 0.0F || 10.0F < rating) {
            valid = false;
        }

        return valid;
    }

    /**
     * Parses a user <code>Opinion</code> to ensure it consists of printable
     * characters and is less than <code>280</code> characters long.
     * 
     * @param opinion <code>String</code> with <code>Opinion</code> to be parsed
     * @return if <code>opinion</code> is valid
     */
    public static boolean parseOpinion(String opinion) {
        boolean valid = true;

        if ( ! opinionChecker.matcher(opinion).matches() ) {
            valid = false;
        }

        return valid;
    }
}