package es.ucm.fdi.business.util;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Iterator;
import java.util.regex.Pattern;

import es.ucm.fdi.integration.data.TagPOJO;
import es.ucm.fdi.integration.data.Location;
import es.ucm.fdi.integration.data.ReviewPOJO;


/**
 * Class with static methods used as a tool to check the validity
 * of arguments, mainly in ProfileManagement methods.
 * 
 * @version 25.05.2018
 */
public class ParsingToolHelper {
    
    private final static int RATE_LOW_LIMIT = 1;
    private final static int RATE_UP_LIMIT = 5;

    /**
     * <p>  A {@code Pattern} to match a valid commercial name: 
     *      scarce limitations.                                 </p>
     * <p>  '^' -> The beginning of the string.                 </p>
     * <p>  '.' -> Any character but endline ('\n').            </p>
     * <p>  '+' -> 1 or more characters of preceding.           </p>
     * <p>  '$' -> The end of the string.                       </p>
     * 
     * @see #parseCommercialName(String)
     */
    public static Pattern commercialNameChecker = Pattern.compile("^.+$");

    /**
     * <p>  A {@code Pattern} to match a valid address: 
     *      scarce limitations.                                 </p>
     * <p>  '^' -> The beginning of the string.                 </p>
     * <p>  '.' -> Any character but endline ('\n').            </p>
     * <p>  '+' -> 1 or more characters of preceding.           </p>
     * <p>  '$' -> The end of the string.                       </p>
     * 
     * @see #parseAddress(String)
     */
    public static Pattern addressChecker = Pattern.compile("^.+$");

    /**
     * <p>  A {@code Pattern} to match a valid tag: just
     *      letters (no accent marks), numbers, underscores and 
     *      at most 30 characters long.                         </p>
     * <p>  '^' -> The beginning of the string.                 </p>
     * <p>  '[\\w]' -> Matches alphanumeric and underscore.     </p>
     * <p>  '{1,30}' -> From 1 to 30 characters of preceding.   </p>
     * <p>  '$' -> The end of the string.                       </p>
     * 
     * @see #parseTags(Collection)
     */
    public static Pattern tagChecker = Pattern.compile("^[\\w]{1,30}$");

    /**
     * <p>  A {@code Pattern} to match a valid tag: just
     *      letters (no accent marks), numbers, underscores and 
     *      at most 30 characters long.                         </p>
     * <p>  '^' -> The beginning of the string.                 </p>
     * <p>  '[\\w]' -> Matches alphanumeric and underscore.     </p>
     * <p>  '{4,32}' -> From 4 to 32 characters of preceding.   </p>
     * <p>  '$' -> The end of the string.                       </p>
     * 
     * @see #parseUsername(Collection)
     */
    public static Pattern usernameChecker = Pattern.compile("^[\\w]{4,32}$");

    /**
     * <p>  A {@code Pattern} to match a valid password: at
     *      least 8 characters including at least a minus, a
     *      mayus and a digit.                                  </p>
     * <p>  '^' -> The beginning of the string.                 </p>
     * <p>  '(?=.*[0-9])' -> Matches a digit (at least).        </p>
     * <p>  '(?=.*[a-z])' -> Matches a minus (at least).        </p>
     * <p>  '(?=.*[A-Z])' -> Matches a mayus (at least).        </p>
     * <p>  '.' -> Any character but endline ('\n').            </p>
     * <p>  '{8,0}' -> Eight or more characters long.
     * <p>  '$' -> The end of the string.                       </p>
     * <p>  Extracted from: 
     *      <a href="https://stackoverflow.com/questions/
     *      3802192/regexp-java-for-password-validation">
     *      StackOverflow</a>                                   </p>
     *
     * @see #parsePassword(String)
     */
    public static Pattern passwordChecker = Pattern.compile(
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$");
       
    /**
     * <p>  A {@code Pattern} to match a valid email.           </p>
     * <p>  Example: A_d+f1-3.h_j22K-M@12N-Pqrs.u12cM.esCOM     </p>
     * <p>  '^' -> The beginning of the string.                 </p>
     * <p>  '([\\w-\\+]+)' -> Matches sequence of alphanum,
     *      underscore, hyphen and plus                         </p>
     * <p>  '(\\.[\\w-]+)*' -> (Optional) After dot, matches 
     *      possible sequence of alphanum, underscore and
     *      hyphen.                                             </p>
     * <p>  '@' -> Matches an at sign.                          </p>
     * <p>  '(([\\w-][^_])+)' -> Matches sequence of alphanum
     *      and hyphen.                                         </p>
     * <p>  '(\\.([\\w][^_])+)?' -> (Optional) After dot, matches
     *      possible sequence of alphanum.                      </p>
     * <p>  '(\\.[A-Za-z]{2,})' -> After dot, matches sequence
     *      of alphabetic, at least 2 characters long.          </p>
     * <p>  '$' -> The end of the string.                       </p>
     * <p>  Extracted from: <a href="https://www.mkyong.com/
     *      regular-expressions/how-to-validate-email-address-
     *      with-regular-expression/"> Mkyong</a>               </p>
     *
     * @see #parseEmail(String)
     */
    public static Pattern emailChecker = Pattern.compile(
            "^([\\w-\\+]+)(\\.[\\w-]+)*@(([\\w-][^_])+)(\\.([A-z0-9])+)?(\\.[A-Za-z]{2,})$");

    /**
     * <p>  A {@code Pattern} to match a valid name.            </p>
     * <p>  Example: Julián Mc'Donald Argüelles                 </p>
     * <p>  '^' -> The beginning of the string.                 </p>
     * <p>  '([A-záéíóúüñ]{2,})' -> Sequence at least 2 character
     *      long of alphabetic (with accent marks).
     * <p>  '\\s' -> A whitespace.                              </p>
     * <p>  '([A-záéíóúüñ]{1,})' -> Sequence at least 1 character  
     *      long of alphabetic (with accent marks).             </p>
     * <p>  '['-]?' -> (Optional) One of thos characters.       </p>
     * <p>  '([A-záéíóúüñ]{2,})' -> Sequence at least 2 character  
     *      long of alphabetic (with accent marks).             </p>
     * <p>  '(\\s[A-záéíóúüñ]{2,})?' -> (Optional) After
     *      whitespace, equence at least 2 character long with
     *      alphabetic (with accent marks).                     </p>
     * <p>  '$' -> The end of the string.                       </p>
     * 
     * @see #parseName(String)
     */
    public static Pattern nameChecker = Pattern
            .compile("^([A-záéíóúüñ]{2,})\\s([A-záéíóúüñ]{1,})('?-?)([A-záéíóúüñ]{2,})(\\s[A-záéíóúüñ]{2,})?$");

       
    
    /**
     * <p>  A {@code Pattern} to match a valid ID.              </p>
     * <p>  '^' -> The beginning of the string.                 </p>
     * <p>  '([\\w][^_])+' -> Matches sequence of alphanum      </p>
     * <p>  '$' -> The end of the string.                       </p>
     * 
     * @see #parseID(String)
     */
    public static Pattern IDChecker = Pattern.compile("^([\\w][^_])+$");
    
       
     
    
     

    

    /**
     * Parses a club {@code Commercial Name} with {@link #commercialNameChecker}.
     *
     * @param commName 
     *          {@code String} with {@code Commercial Name} to be parsed
     * @return 
     *          if {@code commName} is valid
     */
    public static boolean parseCommercialName(String commName) {
        if (!commercialNameChecker.matcher(commName).matches()) {
            return false;
        }

        return true;
    }

    /**
     * Parses a club {@code Address} with {@link #addressChecker}.
     *
     * @param address 
     *          {@code String} with {@code Address} to be parsed
     * @return 
     *          if {@code address} is valid
     */
    public static boolean parseAddress(String address) {
        if (!addressChecker.matcher(address).matches()) {
            return false;
        }

        return true;
    }

    /**
     * Parses a {@code TagPOJO} collection with {@link #tagChecker}
     *
     * @param tags 
     *          - Collection of {@code TagPOJO}s to be parsed
     * 
     * @return 
     *          if {@code tags} collection is valid
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
     * Parses {@code Username} with {@link #usernameChecker}.
     *
     * @param username 
     *          - {@code String} with {@code Username} to be parsed
     * 
     * @return 
     *          if {@code Username} is valid
     */
    public static boolean parseUsername(String username) {
        if (!usernameChecker.matcher(username).matches()) {
            return false;
        }

        return true;
    }

    /**
     * Parses {@code Login Password} with {@link #passwordChecker}.
     *
     * @param password 
     *          - {@code String} with user {@code Login Password} to be parsed
     * @return 
     *          if {@code Login Password} is valid
     */
    public static boolean parsePassword(String password) {
        if (!passwordChecker.matcher(password).matches()) {
            return false;
        }

        return true;
    }

    /**
     * Parses {@code Email} with {@link #emailChecker}.
     *
     * @param email 
     *          - {@code String} with {@code Email} to be parsed
     * @return 
     *          if {@code email} is valid
     */
    public static boolean parseEmail(String email) {
        if (!emailChecker.matcher(email).matches()) {
            return false;
        }

        return true;
    }
    
    /**
     * Parses {@code ID} with {@link #IDChecker}.
     *
     * @param id 
     *          - {@code String} with {@code ID} to be parsed
     * @return 
     *          if {@code ID} is valid
     */
    public static boolean parseID(String id) {
        boolean valid = true;

        if (!IDChecker.matcher(id).matches()) {
            // Invalid ID
            valid = false;
        }

        return valid;
    }

    /**
     * Parses {@code Name} with {@link #nameChecker}.
     *
     * @param name 
     *          - {@code String} with user {@code Name} to be parsed
     * @return 
     *          if {@code name} is valid
     */
    public static boolean parseName(String name) {
        boolean valid = true;

        if (!nameChecker.matcher(name).matches()) {
            valid = false;
        }

        return valid;
    }
    
    
    

    


    /**
     * Parses a user's {@code Birthday} registration to ensure he is +18.
     *
     * @param birthday {@code LocalDate} with {@code Birthday} to be
     * parsed
     * @return if {@code birthday} is valid
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
     * Parses a club {@code TicketPrice} to ensure it is not a negative
     * value.
     *
     * @param price {@code Floar} with {@code TicketPrice} to be
     * parsed
     * @return if {@code price} is valid
     */
    public static boolean parsePrice(float price) {
        boolean valid = true;

        if (price < 0.0f) {
            valid = false;
        }

        return valid;
    }
    
    /**
     * Parses a user {@code Review} to ensure the rate is within limits
     * and the opinion consists of printable characters and is less than 
     * {@code 280} characters long.
     * 
     * @param review {@code ReviewPOJO} with {@code Review} to be parsed
     * @return if {@code review} is valid
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
     * Parses a {@code Location}.
     * 
     * @param location
     *          - {@code Location} to be parsed
     * @return
     *          if location is valid
     */
    public static boolean parseLocation(Location location) {
        return true;
    }
}
