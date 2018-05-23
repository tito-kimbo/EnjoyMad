package es.ucm.fdi.business.util;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Iterator;
import java.util.regex.Pattern;

import es.ucm.fdi.integration.data.TagPOJO;
import es.ucm.fdi.integration.data.ReviewPOJO;


/**
 * Class with static methods used as a tool to check the validity
 * of arguments, mainly in ProfileManagement methods.
 * 
 * @version 15.05.2018
 */
public class ParsingToolHelper {
    
    private final static int RATE_LOW_LIMIT = 0;
    private final static int RATE_UP_LIMIT = 5;
    
    /**
     * <p>
     * A  <code>Pattern</code> to match a password that matches ASCII printable
     * sequence from <code>Whitespace(#32)</code> to <code>~(#126)</code> and
     * size over <code>8</code>.
     * </p>
     * <p>
     * At least minus, mayus and numeric char. No extended <code>ASCII</code>
     * characters allowed (from <code>#126</code> upwards).
     * </p>
     * <p>
     * More details are given on
     *
     * @see
     * <a href="https://stackoverflow.com/questions/3802192/regexp-java-for-password-validation">
     * StackOverflow</a>
     * </p>
     *
     * @see #parsePassword(String)
     */
       public static Pattern passwordChecker = Pattern.compile(
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$");
       
       /**
        * <p>
        * A <code>Pattern</code> that matches an <code>Email Address</code>.
        * </p>
        * <p>
        * Example:
        * </p>
        * <p>
        * ABCdef123.hijKLM@NOPqrs456.ucm.es
        * </p>
        * <p>
        * The regular expression was taken from:
        *
        *  * @see
        * <a href="https://www.mkyong.com/regular-expressions/how-to-validate-email-address-with-regular-expression/">
        * Mkyong</a>
        * </p>
        * @see #parseEmail(String)
        */
       public static Pattern emailChecker = Pattern.compile(
               "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

       
    
    /**
     * A <code>Pattern</code> that matches alphanumeric sequences [A-Za-z0-9_].
     * Used for alphanumeric <code>Strings</code> with no whitespaces and at least
     * one character. And some chars as '_', '/', '[', ...
     * 
     * @see #parseID(String)
     * @see #parseUsername(String)
     */
    public static Pattern alphaNumKeyChecker = Pattern.compile("^([A-z0-9]{1,})$");
    
       
     /**
     * A <code>Pattern</code> that matches a <code>Name</code> with at least one
     * surname. No extended <code>ASCII</code> characters allowed.
     *
     * @see #parseName(String)
     */
    public static Pattern nameChecker = Pattern.compile(
            "^([a-zA-Záéíóúüñ]{2,}\\s[a-zA-záéíóúüñ]{1,}'?-?[a-zA-Záéíóúüñ]{2,}\\s?([a-zA-Záéíóúüñ]{1,})?)");
    
     /**
     * XXX ¿No usar este Pattern?
     * A <code>Pattern</code> that matches an <code>Address</code>>. Characters
     * typically used in an address allowed.
     */
    public static Pattern addressChecker = Pattern.compile("^[A-z0-9\\\\/ÂªÂº,. ]+$");

    /**
     * <p>
     * A <code>Pattern</code> that matches a valid <code>Tag</code>: and 
     * less than <code>25</code> chars long.
     * </p>
     * <p>
     * The <code>Pattern</code> groups the <code>Tag</code> without the
     * <code>#</code>.
     */
    public static Pattern tagChecker = Pattern.compile("^[A-z0-9_]{0,30}$");

    /**
     * Parses <code>Username</code> so it must be an alphanumerical sequence
     * with no whitespaces.
     *
     * @param username <code>String</code> with <code>Username</code> to be
     * parsed
     * @return if <code>Username</code> is valid
     */
    public static boolean parseUsername(String username) {
        boolean valid = true;

        if (!alphaNumKeyChecker.matcher(username).matches()) {
            // Invalid username
            valid = false;
        }

        return valid;
    }
    
    
    /**
     * Parses <code>ID</code> so it must be an alphanumerical sequence with no
     * whitespaces.
     *
     * @param id <code>String</code> with <code>ID</code> to be parsed
     * @return if <code>ID</code> is valid
     */
    public static boolean parseID(String id) {
        boolean valid = true;

        if (!alphaNumKeyChecker.matcher(id).matches()) {
            // Invalid ID
            valid = false;
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

        if (!emailChecker.matcher(email).matches()) {
            valid = false;
        }

        return valid;
    }

    /**
     * Parses a user's <code>Login Password</code> in the moment of account
     * creation to ensure password security. The <code>Password</code> stored in
     * the database will be a hashed key extracted from this
     * <code>Login Password</code>.
     *
     * @param password <code>String</code> with user <code>Login Password</code>
     * to be parsed
     * @return if <code>Login Password</code> is valid
     */
    public static boolean parsePassword(String password) {
        boolean valid = true;

        if (!passwordChecker.matcher(password).matches()) {
            valid = false;
        }

        return valid;
    }


    /**
     * Parses a user's <code>Birthday</code> registration to ensure he is +18.
     *
     * @param birthday <code>LocalDate</code> with <code>Birthday</code> to be
     * parsed
     * @return if <code>birthday</code> is valid
     */
    public static boolean parseBirthday(LocalDate birthday) {
        boolean valid = true;

        LocalDate allowedDate = LocalDate.now();
        allowedDate = allowedDate.minusYears(18);

        if (birthday.isAfter(allowedDate)) {
            valid = false;
        }

        return valid;
    }
    
    
    
    /**
     * Parses a user's <code>Name</code> to check if it is valid: alphabetic
     * characters and with surname.
     *
     * @param name <code>String</code> with user <code>Name</code> to be parsed
     * @return if <code>name</code> is valid
     */
    public static boolean parseName(String name) {
        boolean valid = true;

        if (!nameChecker.matcher(name).matches()) {
            valid = false;
        }

        return valid;
    }

    
    /**
     * Parses a club <code>Address</code>, with the typical used characters in
     * an address.
     *
     * @param address <code>String</code> with <code>Address</code> to be parsed
     * @return if <code>address</code> is valid
     */
    public static boolean parseAddress(String address) {
        return true;
    }
    

    /**
     * Parses a club <code>Commercial Name</code>. Initially no conditions.
     *
     * @param commName <code>String</code> with <code>Commercial Name</code> to
     * be parsed
     * @return if <code>commName</code> is valid
     */
    public static boolean parseCommercialName(String commName) {
        return true;
    }


    /**
     * Parses a club <code>TicketPrice</code> to ensure it is not a negative
     * value.
     *
     * @param price <code>Floar</code> with <code>TicketPrice</code> to be
     * parsed
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
     * Parses a user <code>Rate</code> to ensure it is an <code>Integer</code>
     * between <code>0-10</code>.
     *
     * @param rate <code>Integer</code> with user <code>Rating</code> to be
     * parsed
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
     * Parses a {@code TagPOJO} collection to ensure every {@code TagPOJO} 
     * is correct.
     *
     * @param tags 
     *          - Collection of {@code TagPOJO}s to be parsed
     * 
     * @return if {@code tags} collection is valid
     */
    public static boolean parseTags(Collection<TagPOJO> tags) {
        boolean valid = true;

        Iterator<TagPOJO> iter = tags.iterator();

        while (valid && iter.hasNext()) {
            TagPOJO tag = iter.next();

            if ( ! tagChecker.matcher(tag.getTag()).matches() ) {
                valid = false;
            }
        }
        
        return valid;
    }

    
    /**
     * Parses a user <code>Review</code> to ensure the rate is within limits
     * and the opinion consists of printable characters and is less than 
     * <code>280</code> characters long.
     * 
     * @param review <code>ReviewPOJO</code> with <code>Review</code> to be parsed
     * @return if <code>review</code> is valid
     */
    public static boolean parseReview(ReviewPOJO review) {
        boolean valid = true;
        
        if ( RATE_LOW_LIMIT > review.getRating() 
                || RATE_UP_LIMIT < review.getRating() ) {

            valid = false;
        }

        return valid;
    }
    

    /**
     * Parses a club <code>Rating</code> to ensure it is a <code>Float</code>
     * between <code>0-5</code>.
     *
     * @param rating <code>Float</code> with <code>Rating</code> to be parsed
     * @return if <code>rating</code> is between bounds
     */
    public static boolean parseRating(Float rating) {
        boolean valid = true;

        if ( RATE_LOW_LIMIT > rating 
                || RATE_UP_LIMIT < rating) {

            valid = false;
        }

        return valid;
    }
}
