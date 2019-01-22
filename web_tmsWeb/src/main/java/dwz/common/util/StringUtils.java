package dwz.common.util;

import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import dwz.common.util.time.DateUtils;
import java.io.UnsupportedEncodingException;

/**
 *
 */
public class StringUtils {

    /**
     * <p>
     * The maximum size to which the padding constant(s) can expand.</p>
     */
    private static final int PAD_LIMIT = 8192;

    /**
     * The empty String <code>""</code>.
     *
     * @since 2.0
     */
    public static final String EMPTY = "";

    // IndexOf
    //-----------------------------------------------------------------------
    /**
     * <p>
     * Finds the first index within a String, handling <code>null</code>. This
     * method uses {@link String#indexOf(int)}.</p>
     *
     * <p>
     * A <code>null</code> or empty ("") String will return <code>-1</code>.</p>
     *
     * <pre>
     * StringUtils.indexOf(null, *)         = -1
     * StringUtils.indexOf("", *)           = -1
     * StringUtils.indexOf("aabaabaa", 'a') = 0
     * StringUtils.indexOf("aabaabaa", 'b') = 2
     * </pre>
     *
     * @param str the String to check, may be null
     * @param searchChar the character to find
     * @return the first index of the search character, -1 if no match or
     * <code>null</code> string input
     * @since 2.0
     */
    public static int indexOf(String str, char searchChar) {
        if (isEmpty(str)) {
            return -1;
        }
        return str.indexOf(searchChar);
    }

    /**
     * <p>
     * Finds the first index within a String from a start position, handling
     * <code>null</code>. This method uses {@link String#indexOf(int, int)}.</p>
     *
     * <p>
     * A <code>null</code> or empty ("") String will return <code>-1</code>. A
     * negative start position is treated as zero. A start position greater than
     * the string length returns <code>-1</code>.</p>
     *
     * <pre>
     * StringUtils.indexOf(null, *, *)          = -1
     * StringUtils.indexOf("", *, *)            = -1
     * StringUtils.indexOf("aabaabaa", 'b', 0)  = 2
     * StringUtils.indexOf("aabaabaa", 'b', 3)  = 5
     * StringUtils.indexOf("aabaabaa", 'b', 9)  = -1
     * StringUtils.indexOf("aabaabaa", 'b', -1) = 2
     * </pre>
     *
     * @param str the String to check, may be null
     * @param searchChar the character to find
     * @param startPos the start position, negative treated as zero
     * @return the first index of the search character, -1 if no match or
     * <code>null</code> string input
     * @since 2.0
     */
    public static int indexOf(String str, char searchChar, int startPos) {
        if (isEmpty(str)) {
            return -1;
        }
        return str.indexOf(searchChar, startPos);
    }

    /**
     * <p>
     * Finds the first index within a String, handling <code>null</code>. This
     * method uses {@link String#indexOf(String)}.</p>
     *
     * <p>
     * A <code>null</code> String will return <code>-1</code>.</p>
     *
     * <pre>
     * StringUtils.indexOf(null, *)          = -1
     * StringUtils.indexOf(*, null)          = -1
     * StringUtils.indexOf("", "")           = 0
     * StringUtils.indexOf("aabaabaa", "a")  = 0
     * StringUtils.indexOf("aabaabaa", "b")  = 2
     * StringUtils.indexOf("aabaabaa", "ab") = 1
     * StringUtils.indexOf("aabaabaa", "")   = 0
     * </pre>
     *
     * @param str the String to check, may be null
     * @param searchStr the String to find, may be null
     * @return the first index of the search String, -1 if no match or
     * <code>null</code> string input
     * @since 2.0
     */
    public static int indexOf(String str, String searchStr) {
        if (str == null || searchStr == null) {
            return -1;
        }
        return str.indexOf(searchStr);
    }

    // LastIndexOf
    //-----------------------------------------------------------------------
    /**
     * <p>
     * Finds the last index within a String, handling <code>null</code>. This
     * method uses {@link String#lastIndexOf(int)}.</p>
     *
     * <p>
     * A <code>null</code> or empty ("") String will return <code>-1</code>.</p>
     *
     * <pre>
     * StringUtils.lastIndexOf(null, *)         = -1
     * StringUtils.lastIndexOf("", *)           = -1
     * StringUtils.lastIndexOf("aabaabaa", 'a') = 7
     * StringUtils.lastIndexOf("aabaabaa", 'b') = 5
     * </pre>
     *
     * @param str the String to check, may be null
     * @param searchChar the character to find
     * @return the last index of the search character, -1 if no match or
     * <code>null</code> string input
     * @since 2.0
     */
    public static int lastIndexOf(String str, char searchChar) {
        if (isEmpty(str)) {
            return -1;
        }
        return str.lastIndexOf(searchChar);
    }

    /**
     * <p>
     * Finds the last index within a String from a start position, handling
     * <code>null</code>. This method uses
     * {@link String#lastIndexOf(int, int)}.</p>
     *
     * <p>
     * A <code>null</code> or empty ("") String will return <code>-1</code>. A
     * negative start position returns <code>-1</code>. A start position greater
     * than the string length searches the whole string.</p>
     *
     * <pre>
     * StringUtils.lastIndexOf(null, *, *)          = -1
     * StringUtils.lastIndexOf("", *,  *)           = -1
     * StringUtils.lastIndexOf("aabaabaa", 'b', 8)  = 5
     * StringUtils.lastIndexOf("aabaabaa", 'b', 4)  = 2
     * StringUtils.lastIndexOf("aabaabaa", 'b', 0)  = -1
     * StringUtils.lastIndexOf("aabaabaa", 'b', 9)  = 5
     * StringUtils.lastIndexOf("aabaabaa", 'b', -1) = -1
     * StringUtils.lastIndexOf("aabaabaa", 'a', 0)  = 0
     * </pre>
     *
     * @param str the String to check, may be null
     * @param searchChar the character to find
     * @param startPos the start position
     * @return the last index of the search character, -1 if no match or
     * <code>null</code> string input
     * @since 2.0
     */
    public static int lastIndexOf(String str, char searchChar, int startPos) {
        if (isEmpty(str)) {
            return -1;
        }
        return str.lastIndexOf(searchChar, startPos);
    }

    /**
     * <p>
     * Finds the last index within a String, handling <code>null</code>. This
     * method uses {@link String#lastIndexOf(String)}.</p>
     *
     * <p>
     * A <code>null</code> String will return <code>-1</code>.</p>
     *
     * <pre>
     * StringUtils.lastIndexOf(null, *)          = -1
     * StringUtils.lastIndexOf(*, null)          = -1
     * StringUtils.lastIndexOf("", "")           = 0
     * StringUtils.lastIndexOf("aabaabaa", "a")  = 0
     * StringUtils.lastIndexOf("aabaabaa", "b")  = 2
     * StringUtils.lastIndexOf("aabaabaa", "ab") = 1
     * StringUtils.lastIndexOf("aabaabaa", "")   = 8
     * </pre>
     *
     * @param str the String to check, may be null
     * @param searchStr the String to find, may be null
     * @return the last index of the search String, -1 if no match or
     * <code>null</code> string input
     * @since 2.0
     */
    public static int lastIndexOf(String str, String searchStr) {
        if (str == null || searchStr == null) {
            return -1;
        }
        return str.lastIndexOf(searchStr);
    }

    /**
     * <p>
     * Finds the first index within a String, handling <code>null</code>. This
     * method uses {@link String#lastIndexOf(String, int)}.</p>
     *
     * <p>
     * A <code>null</code> String will return <code>-1</code>. A negative start
     * position returns <code>-1</code>. An empty ("") search String always
     * matches unless the start position is negative. A start position greater
     * than the string length searches the whole string.</p>
     *
     * <pre>
     * StringUtils.lastIndexOf(null, *, *)          = -1
     * StringUtils.lastIndexOf(*, null, *)          = -1
     * StringUtils.lastIndexOf("aabaabaa", "a", 8)  = 7
     * StringUtils.lastIndexOf("aabaabaa", "b", 8)  = 5
     * StringUtils.lastIndexOf("aabaabaa", "ab", 8) = 4
     * StringUtils.lastIndexOf("aabaabaa", "b", 9)  = 5
     * StringUtils.lastIndexOf("aabaabaa", "b", -1) = -1
     * StringUtils.lastIndexOf("aabaabaa", "a", 0)  = 0
     * StringUtils.lastIndexOf("aabaabaa", "b", 0)  = -1
     * </pre>
     *
     * @param str the String to check, may be null
     * @param searchStr the String to find, may be null
     * @param startPos the start position, negative treated as zero
     * @return the first index of the search String, -1 if no match or
     * <code>null</code> string input
     * @since 2.0
     */
    public static int lastIndexOf(String str, String searchStr, int startPos) {
        if (str == null || searchStr == null) {
            return -1;
        }
        return str.lastIndexOf(searchStr, startPos);
    }

    // Contains
    //-----------------------------------------------------------------------
    /**
     * <p>
     * Checks if String contains a search character, handling <code>null</code>.
     * This method uses {@link String#indexOf(int)}.</p>
     *
     * <p>
     * A <code>null</code> or empty ("") String will return
     * <code>false</code>.</p>
     *
     * <pre>
     * StringUtils.contains(null, *)    = false
     * StringUtils.contains("", *)      = false
     * StringUtils.contains("abc", 'a') = true
     * StringUtils.contains("abc", 'z') = false
     * </pre>
     *
     * @param str the String to check, may be null
     * @param searchChar the character to find
     * @return true if the String contains the search character, false if not or
     * <code>null</code> string input
     * @since 2.0
     */
    public static boolean contains(String str, char searchChar) {
        if (isEmpty(str)) {
            return false;
        }
        return str.indexOf(searchChar) >= 0;
    }

    /**
     * <p>
     * Checks if String contains a search String, handling <code>null</code>.
     * This method uses {@link String#indexOf(String)}.</p>
     *
     * <p>
     * A <code>null</code> String will return <code>false</code>.</p>
     *
     * <pre>
     * StringUtils.contains(null, *)     = false
     * StringUtils.contains(*, null)     = false
     * StringUtils.contains("", "")      = true
     * StringUtils.contains("abc", "")   = true
     * StringUtils.contains("abc", "a")  = true
     * StringUtils.contains("abc", "z")  = false
     * </pre>
     *
     * @param str the String to check, may be null
     * @param searchStr the String to find, may be null
     * @return true if the String contains the search String, false if not or
     * <code>null</code> string input
     * @since 2.0
     */
    public static boolean contains(String str, String searchStr) {
        if (str == null || searchStr == null) {
            return false;
        }
        return str.contains(searchStr);
    }

    // Substring
    //-----------------------------------------------------------------------
    /**
     * <p>
     * Gets a substring from the specified String avoiding exceptions.</p>
     *
     * <p>
     * A negative start position can be used to start <code>n</code> characters
     * from the end of the String.</p>
     *
     * <p>
     * A <code>null</code> String will return <code>null</code>. An empty ("")
     * String will return "".</p>
     *
     * <pre>
     * StringUtils.substring(null, *)   = null
     * StringUtils.substring("", *)     = ""
     * StringUtils.substring("abc", 0)  = "abc"
     * StringUtils.substring("abc", 2)  = "c"
     * StringUtils.substring("abc", 4)  = ""
     * StringUtils.substring("abc", -2) = "bc"
     * StringUtils.substring("abc", -4) = "abc"
     * </pre>
     *
     * @param str the String to get the substring from, may be null
     * @param start the position to start from, negative means count back from
     * the end of the String by this many characters
     * @return substring from start position, <code>null</code> if null String
     * input
     */
    public static String substring(String str, int start) {
        if (str == null) {
            return null;
        }

        // handle negatives, which means last n characters
        if (start < 0) {
            start = str.length() + start; // remember start is negative
        }

        if (start < 0) {
            start = 0;
        }
        if (start > str.length()) {
            return EMPTY;
        }

        return str.substring(start);
    }

    /**
     * <p>
     * Gets a substring from the specified String avoiding exceptions.</p>
     *
     * <p>
     * A negative start position can be used to start/end <code>n</code>
     * characters from the end of the String.</p>
     *
     * <p>
     * The returned substring starts with the character in the
     * <code>start</code> position and ends before the <code>end</code>
     * position. All position counting is zero-based -- i.e., to start at the
     * beginning of the string use <code>start = 0</code>. Negative start and
     * end positions can be used to specify offsets relative to the end of the
     * String.</p>
     *
     * <p>
     * If <code>start</code> is not strictly to the left of <code>end</code>, ""
     * is returned.</p>
     *
     * <pre>
     * StringUtils.substring(null, *, *)    = null
     * StringUtils.substring("", * ,  *)    = "";
     * StringUtils.substring("abc", 0, 2)   = "ab"
     * StringUtils.substring("abc", 2, 0)   = ""
     * StringUtils.substring("abc", 2, 4)   = "c"
     * StringUtils.substring("abc", 4, 6)   = ""
     * StringUtils.substring("abc", 2, 2)   = ""
     * StringUtils.substring("abc", -2, -1) = "b"
     * StringUtils.substring("abc", -4, 2)  = "ab"
     * </pre>
     *
     * @param str the String to get the substring from, may be null
     * @param start the position to start from, negative means count back from
     * the end of the String by this many characters
     * @param end the position to end at (exclusive), negative means count back
     * from the end of the String by this many characters
     * @return substring from start position to end positon, <code>null</code>
     * if null String input
     */
    public static String substring(String str, int start, int end) {
        if (str == null) {
            return null;
        }

        // handle negatives
        if (end < 0) {
            end = str.length() + end; // remember end is negative
        }
        if (start < 0) {
            start = str.length() + start; // remember start is negative
        }

        // check length next
        if (end > str.length()) {
            end = str.length();
        }

        // if start is greater than end, return ""
        if (start > end) {
            return EMPTY;
        }

        if (start < 0) {
            start = 0;
        }
        if (end < 0) {
            end = 0;
        }

        return str.substring(start, end);
    }

    // Left/Right/Mid
    //-----------------------------------------------------------------------
    /**
     * <p>
     * Gets the leftmost <code>len</code> characters of a String.</p>
     *
     * <p>
     * If <code>len</code> characters are not available, or the String is
     * <code>null</code>, the String will be returned without an exception. An
     * exception is thrown if len is negative.</p>
     *
     * <pre>
     * StringUtils.left(null, *)    = null
     * StringUtils.left(*, -ve)     = ""
     * StringUtils.left("", *)      = ""
     * StringUtils.left("abc", 0)   = ""
     * StringUtils.left("abc", 2)   = "ab"
     * StringUtils.left("abc", 4)   = "abc"
     * </pre>
     *
     * @param str the String to get the leftmost characters from, may be null
     * @param len the length of the required String, must be zero or positive
     * @return the leftmost characters, <code>null</code> if null String input
     */
    public static String left(String str, int len) {
        if (str == null) {
            return null;
        }
        if (len < 0) {
            return EMPTY;
        }
        if (str.length() <= len) {
            return str;
        }
        return str.substring(0, len);
    }

    /**
     * <p>
     * Gets the rightmost <code>len</code> characters of a String.</p>
     *
     * <p>
     * If <code>len</code> characters are not available, or the String is
     * <code>null</code>, the String will be returned without an an exception.
     * An exception is thrown if len is negative.</p>
     *
     * <pre>
     * StringUtils.right(null, *)    = null
     * StringUtils.right(*, -ve)     = ""
     * StringUtils.right("", *)      = ""
     * StringUtils.right("abc", 0)   = ""
     * StringUtils.right("abc", 2)   = "bc"
     * StringUtils.right("abc", 4)   = "abc"
     * </pre>
     *
     * @param str the String to get the rightmost characters from, may be null
     * @param len the length of the required String, must be zero or positive
     * @return the rightmost characters, <code>null</code> if null String input
     */
    public static String right(String str, int len) {
        if (str == null) {
            return null;
        }
        if (len < 0) {
            return EMPTY;
        }
        if (str.length() <= len) {
            return str;
        }
        return str.substring(str.length() - len);
    }

    /**
     * <p>
     * Gets <code>len</code> characters from the middle of a String.</p>
     *
     * <p>
     * If <code>len</code> characters are not available, the remainder of the
     * String will be returned without an exception. If the String is
     * <code>null</code>, <code>null</code> will be returned. An exception is
     * thrown if len is negative.</p>
     *
     * <pre>
     * StringUtils.mid(null, *, *)    = null
     * StringUtils.mid(*, *, -ve)     = ""
     * StringUtils.mid("", 0, *)      = ""
     * StringUtils.mid("abc", 0, 2)   = "ab"
     * StringUtils.mid("abc", 0, 4)   = "abc"
     * StringUtils.mid("abc", 2, 4)   = "c"
     * StringUtils.mid("abc", 4, 2)   = ""
     * StringUtils.mid("abc", -2, 2)  = "ab"
     * </pre>
     *
     * @param str the String to get the characters from, may be null
     * @param pos the position to start from, negative treated as zero
     * @param len the length of the required String, must be zero or positive
     * @return the middle characters, <code>null</code> if null String input
     */
    public static String mid(String str, int pos, int len) {
        if (str == null) {
            return null;
        }
        if (len < 0 || pos > str.length()) {
            return EMPTY;
        }
        if (pos < 0) {
            pos = 0;
        }
        if (str.length() <= (pos + len)) {
            return str.substring(pos);
        }
        return str.substring(pos, pos + len);
    }

    /**
     * <p>
     * Joins the elements of the provided array into a single String containing
     * the provided list of elements.</p>
     *
     * <p>
     * No separator is added to the joined String. Null objects or empty strings
     * within the array are represented by empty strings.</p>
     *
     * <pre>
     * StringUtils.join(null)            = null
     * StringUtils.join([])              = ""
     * StringUtils.join([null])          = ""
     * StringUtils.join(["a", "b", "c"]) = "abc"
     * StringUtils.join([null, "", "a"]) = "a"
     * </pre>
     *
     * @param array the array of values to join together, may be null
     * @return the joined String, <code>null</code> if null array input
     * @since 2.0
     */
    public static String join(Object[] array) {
        return join(array, null);
    }

    /**
     * <p>
     * Joins the elements of the provided array into a single String containing
     * the provided list of elements.</p>
     *
     * <p>
     * No delimiter is added before or after the list. Null objects or empty
     * strings within the array are represented by empty strings.</p>
     *
     * <pre>
     * StringUtils.join(null, *)               = null
     * StringUtils.join([], *)                 = ""
     * StringUtils.join([null], *)             = ""
     * StringUtils.join(["a", "b", "c"], ';')  = "a;b;c"
     * StringUtils.join(["a", "b", "c"], null) = "abc"
     * StringUtils.join([null, "", "a"], ';')  = ";;a"
     * </pre>
     *
     * @param array the array of values to join together, may be null
     * @param separator the separator character to use
     * @return the joined String, <code>null</code> if null array input
     * @since 2.0
     */
    public static String join(Object[] array, char separator) {
        if (array == null) {
            return null;
        }

        return join(array, separator, 0, array.length);
    }

    /**
     * <p>
     * Joins the elements of the provided array into a single String containing
     * the provided list of elements.</p>
     *
     * <p>
     * No delimiter is added before or after the list. Null objects or empty
     * strings within the array are represented by empty strings.</p>
     *
     * <pre>
     * StringUtils.join(null, *)               = null
     * StringUtils.join([], *)                 = ""
     * StringUtils.join([null], *)             = ""
     * StringUtils.join(["a", "b", "c"], ';')  = "a;b;c"
     * StringUtils.join(["a", "b", "c"], null) = "abc"
     * StringUtils.join([null, "", "a"], ';')  = ";;a"
     * </pre>
     *
     * @param array the array of values to join together, may be null
     * @param separator the separator character to use
     * @param startIndex the first index to start joining from. It is an error
     * to pass in an end index past the end of the array
     * @param endIndex the index to stop joining from (exclusive). It is an
     * error to pass in an end index past the end of the array
     * @return the joined String, <code>null</code> if null array input
     * @since 2.0
     */
    public static String join(Object[] array, char separator, int startIndex, int endIndex) {
        if (array == null) {
            return null;
        }
        int bufSize = (endIndex - startIndex);
        if (bufSize <= 0) {
            return EMPTY;
        }

        bufSize *= ((array[startIndex] == null ? 16 : array[startIndex].toString().length()) + 1);
        StringBuilder buf = new StringBuilder(bufSize);

        for (int i = startIndex; i < endIndex; i++) {
            if (i > startIndex) {
                buf.append(separator);
            }
            if (array[i] != null) {
                buf.append(array[i]);
            }
        }
        return buf.toString();
    }

    /**
     * <p>
     * Joins the elements of the provided array into a single String containing
     * the provided list of elements.</p>
     *
     * <p>
     * No delimiter is added before or after the list. A <code>null</code>
     * separator is the same as an empty String (""). Null objects or empty
     * strings within the array are represented by empty strings.</p>
     *
     * <pre>
     * StringUtils.join(null, *)                = null
     * StringUtils.join([], *)                  = ""
     * StringUtils.join([null], *)              = ""
     * StringUtils.join(["a", "b", "c"], "--")  = "a--b--c"
     * StringUtils.join(["a", "b", "c"], null)  = "abc"
     * StringUtils.join(["a", "b", "c"], "")    = "abc"
     * StringUtils.join([null, "", "a"], ',')   = ",,a"
     * </pre>
     *
     * @param array the array of values to join together, may be null
     * @param separator the separator character to use, null treated as ""
     * @return the joined String, <code>null</code> if null array input
     */
    public static String join(Object[] array, String separator) {
        if (array == null) {
            return null;
        }
        return join(array, separator, 0, array.length);
    }

    /**
     * <p>
     * Joins the elements of the provided array into a single String containing
     * the provided list of elements.</p>
     *
     * <p>
     * No delimiter is added before or after the list. A <code>null</code>
     * separator is the same as an empty String (""). Null objects or empty
     * strings within the array are represented by empty strings.</p>
     *
     * <pre>
     * StringUtils.join(null, *)                = null
     * StringUtils.join([], *)                  = ""
     * StringUtils.join([null], *)              = ""
     * StringUtils.join(["a", "b", "c"], "--")  = "a--b--c"
     * StringUtils.join(["a", "b", "c"], null)  = "abc"
     * StringUtils.join(["a", "b", "c"], "")    = "abc"
     * StringUtils.join([null, "", "a"], ',')   = ",,a"
     * </pre>
     *
     * @param array the array of values to join together, may be null
     * @param separator the separator character to use, null treated as ""
     * @param startIndex the first index to start joining from. It is an error
     * to pass in an end index past the end of the array
     * @param endIndex the index to stop joining from (exclusive). It is an
     * error to pass in an end index past the end of the array
     * @return the joined String, <code>null</code> if null array input
     */
    public static String join(Object[] array, String separator, int startIndex, int endIndex) {
        if (array == null) {
            return null;
        }
        if (separator == null) {
            separator = EMPTY;
        }

        // endIndex - startIndex > 0:   Len = NofStrings *(len(firstString) + len(separator))
        //           (Assuming that all Strings are roughly equally long)
        int bufSize = (endIndex - startIndex);
        if (bufSize <= 0) {
            return EMPTY;
        }

        bufSize *= ((array[startIndex] == null ? 16 : array[startIndex].toString().length()) + separator.length());

        StringBuilder buf = new StringBuilder(bufSize);

        for (int i = startIndex; i < endIndex; i++) {
            if (i > startIndex) {
                buf.append(separator);
            }
            if (array[i] != null) {
                buf.append(array[i]);
            }
        }
        return buf.toString();
    }

    // Delete
    //-----------------------------------------------------------------------
    /**
     * <p>
     * Deletes all whitespaces from a String as defined by
     * {@link Character#isWhitespace(char)}.</p>
     *
     * <pre>
     * StringUtils.deleteWhitespace(null)         = null
     * StringUtils.deleteWhitespace("")           = ""
     * StringUtils.deleteWhitespace("abc")        = "abc"
     * StringUtils.deleteWhitespace("   ab  c  ") = "abc"
     * </pre>
     *
     * @param str the String to delete whitespace from, may be null
     * @return the String without whitespaces, <code>null</code> if null String
     * input
     */
    public static String deleteWhitespace(String str) {
        if (isEmpty(str)) {
            return str;
        }
        int sz = str.length();
        char[] chs = new char[sz];
        int count = 0;
        for (int i = 0; i < sz; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                chs[count++] = str.charAt(i);
            }
        }
        if (count == sz) {
            return str;
        }
        return new String(chs, 0, count);
    }

    // Remove
    //-----------------------------------------------------------------------
    /**
     * <p>
     * Removes a substring only if it is at the begining of a source string,
     * otherwise returns the source string.</p>
     *
     * <p>
     * A <code>null</code> source string will return <code>null</code>. An empty
     * ("") source string will return the empty string. A <code>null</code>
     * search string will return the source string.</p>
     *
     * <pre>
     * StringUtils.removeStart(null, *)      = null
     * StringUtils.removeStart("", *)        = ""
     * StringUtils.removeStart(*, null)      = *
     * StringUtils.removeStart("www.domain.com", "www.")   = "domain.com"
     * StringUtils.removeStart("domain.com", "www.")       = "domain.com"
     * StringUtils.removeStart("www.domain.com", "domain") = "www.domain.com"
     * StringUtils.removeStart("abc", "")    = "abc"
     * </pre>
     *
     * @param str the source String to search, may be null
     * @param remove the String to search for and remove, may be null
     * @return the substring with the string removed if found, <code>null</code>
     * if null String input
     * @since 2.1
     */
    public static String removeStart(String str, String remove) {
        if (isEmpty(str) || isEmpty(remove)) {
            return str;
        }
        if (str.startsWith(remove)) {
            return str.substring(remove.length());
        }
        return str;
    }

    /**
     * <p>
     * Removes a substring only if it is at the end of a source string,
     * otherwise returns the source string.</p>
     *
     * <p>
     * A <code>null</code> source string will return <code>null</code>. An empty
     * ("") source string will return the empty string. A <code>null</code>
     * search string will return the source string.</p>
     *
     * <pre>
     * StringUtils.removeEnd(null, *)      = null
     * StringUtils.removeEnd("", *)        = ""
     * StringUtils.removeEnd(*, null)      = *
     * StringUtils.removeEnd("www.domain.com", ".com.")  = "www.domain.com"
     * StringUtils.removeEnd("www.domain.com", ".com")   = "www.domain"
     * StringUtils.removeEnd("www.domain.com", "domain") = "www.domain.com"
     * StringUtils.removeEnd("abc", "")    = "abc"
     * </pre>
     *
     * @param str the source String to search, may be null
     * @param remove the String to search for and remove, may be null
     * @return the substring with the string removed if found, <code>null</code>
     * if null String input
     * @since 2.1
     */
    public static String removeEnd(String str, String remove) {
        if (isEmpty(str) || isEmpty(remove)) {
            return str;
        }
        if (str.endsWith(remove)) {
            return str.substring(0, str.length() - remove.length());
        }
        return str;
    }

    /**
     * <p>
     * Removes all occurrences of a character from within the source string.</p>
     *
     * <p>
     * A <code>null</code> source string will return <code>null</code>. An empty
     * ("") source string will return the empty string.</p>
     *
     * <pre>
     * StringUtils.remove(null, *)       = null
     * StringUtils.remove("", *)         = ""
     * StringUtils.remove("queued", 'u') = "qeed"
     * StringUtils.remove("queued", 'z') = "queued"
     * </pre>
     *
     * @param str the source String to search, may be null
     * @param remove the char to search for and remove, may be null
     * @return the substring with the char removed if found, <code>null</code>
     * if null String input
     * @since 2.1
     */
    public static String remove(String str, char remove) {
        if (isEmpty(str) || str.indexOf(remove) == -1) {
            return str;
        }
        char[] chars = str.toCharArray();
        int pos = 0;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] != remove) {
                chars[pos++] = chars[i];
            }
        }
        return new String(chars, 0, pos);
    }

    /**
     * <p>
     * Removes all occurrences of a substring from within the source string.</p>
     *
     * <p>
     * A <code>null</code> source string will return <code>null</code>. An empty
     * ("") source string will return the empty string. A <code>null</code>
     * remove string will return the source string. An empty ("") remove string
     * will return the source string.</p>
     *
     * <pre>
     * StringUtils.remove(null, *)        = null
     * StringUtils.remove("", *)          = ""
     * StringUtils.remove(*, null)        = *
     * StringUtils.remove(*, "")          = *
     * StringUtils.remove("queued", "ue") = "qd"
     * StringUtils.remove("queued", "zz") = "queued"
     * </pre>
     *
     * @param str the source String to search, may be null
     * @param remove the String to search for and remove, may be null
     * @return the substring with the string removed if found, <code>null</code>
     * if null String input
     * @since 2.1
     */
    public static String remove(String str, String remove) {
        if (isEmpty(str) || isEmpty(remove)) {
            return str;
        }
        return replace(str, remove, EMPTY, -1);
    }

    // Empty checks
    //-----------------------------------------------------------------------
    /**
     * <p>
     * Checks if a String is empty ("") or null.</p>
     *
     * <pre>
     * StringUtils.isEmpty(null)      = true
     * StringUtils.isEmpty("")        = true
     * StringUtils.isEmpty(" ")       = false
     * StringUtils.isEmpty("bob")     = false
     * StringUtils.isEmpty("  bob  ") = false
     * </pre>
     *
     * <p>
     * NOTE: This method changed in Lang version 2.0. It no longer trims the
     * String. That functionality is available in isBlank().</p>
     *
     * @param str the String to check, may be null
     * @return <code>true</code> if the String is empty or null
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * <p>
     * Checks if a String is not empty ("") and not null.</p>
     *
     * <pre>
     * StringUtils.isNotEmpty(null)      = false
     * StringUtils.isNotEmpty("")        = false
     * StringUtils.isNotEmpty(" ")       = true
     * StringUtils.isNotEmpty("bob")     = true
     * StringUtils.isNotEmpty("  bob  ") = true
     * </pre>
     *
     * @param str the String to check, may be null
     * @return <code>true</code> if the String is not empty and not null
     */
    public static boolean isNotEmpty(String str) {
        return !StringUtils.isEmpty(str);
    }

    /**
     * <p>
     * Checks if a String is whitespace, empty ("") or null.</p>
     *
     * <pre>
     * StringUtils.isBlank(null)      = true
     * StringUtils.isBlank("")        = true
     * StringUtils.isBlank(" ")       = true
     * StringUtils.isBlank("bob")     = false
     * StringUtils.isBlank("  bob  ") = false
     * </pre>
     *
     * @param str the String to check, may be null
     * @return <code>true</code> if the String is null, empty or whitespace
     * @since 2.0
     */
    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>
     * Checks if a String is not empty (""), not null and not whitespace
     * only.</p>
     *
     * <pre>
     * StringUtils.isNotBlank(null)      = false
     * StringUtils.isNotBlank("")        = false
     * StringUtils.isNotBlank(" ")       = false
     * StringUtils.isNotBlank("bob")     = true
     * StringUtils.isNotBlank("  bob  ") = true
     * </pre>
     *
     * @param str the String to check, may be null
     * @return <code>true</code> if the String is not empty and not null and not
     * whitespace
     * @since 2.0
     */
    public static boolean isNotBlank(String str) {
        return !StringUtils.isBlank(str);
    }

    /**
     * <p>
     * Compares two Strings, returning <code>true</code> if they are equal.</p>
     *
     * <p>
     * <code>null</code>s are handled without exceptions. Two <code>null</code>
     * references are considered to be equal. The comparison is case
     * sensitive.</p>
     *
     * <pre>
     * StringUtils.equals(null, null)   = true
     * StringUtils.equals(null, "abc")  = false
     * StringUtils.equals("abc", null)  = false
     * StringUtils.equals("abc", "abc") = true
     * StringUtils.equals("abc", "ABC") = false
     * </pre>
     *
     * @see java.lang.String#equals(Object)
     * @param str1 the first String, may be null
     * @param str2 the second String, may be null
     * @return <code>true</code> if the Strings are equal, case sensitive, or
     * both <code>null</code>
     */
    public static boolean equals(String str1, String str2) {
        return str1 == null ? str2 == null : str1.equals(str2);
    }

    /**
     * <p>
     * Compares two Strings, returning <code>true</code> if they are equal
     * ignoring the case.</p>
     *
     * <p>
     * <code>null</code>s are handled without exceptions. Two <code>null</code>
     * references are considered equal. Comparison is case insensitive.</p>
     *
     * <pre>
     * StringUtils.equalsIgnoreCase(null, null)   = true
     * StringUtils.equalsIgnoreCase(null, "abc")  = false
     * StringUtils.equalsIgnoreCase("abc", null)  = false
     * StringUtils.equalsIgnoreCase("abc", "abc") = true
     * StringUtils.equalsIgnoreCase("abc", "ABC") = true
     * </pre>
     *
     * @see java.lang.String#equalsIgnoreCase(String)
     * @param str1 the first String, may be null
     * @param str2 the second String, may be null
     * @return <code>true</code> if the Strings are equal, case insensitive, or
     * both <code>null</code>
     */
    public static boolean equalsIgnoreCase(String str1, String str2) {
        return str1 == null ? str2 == null : str1.equalsIgnoreCase(str2);
    }

    // Replacing
    //-----------------------------------------------------------------------
    /**
     * <p>
     * Replaces a String with another String inside a larger String, once.</p>
     *
     * <p>
     * A <code>null</code> reference passed to this method is a no-op.</p>
     *
     * <pre>
     * StringUtils.replaceOnce(null, *, *)        = null
     * StringUtils.replaceOnce("", *, *)          = ""
     * StringUtils.replaceOnce("any", null, *)    = "any"
     * StringUtils.replaceOnce("any", *, null)    = "any"
     * StringUtils.replaceOnce("any", "", *)      = "any"
     * StringUtils.replaceOnce("aba", "a", null)  = "aba"
     * StringUtils.replaceOnce("aba", "a", "")    = "ba"
     * StringUtils.replaceOnce("aba", "a", "z")   = "zba"
     * </pre>
     *
     * @see #replace(String text, String searchString, String replacement, int
     * max)
     * @param text text to search and replace in, may be null
     * @param searchString the String to search for, may be null
     * @param replacement the String to replace with, may be null
     * @return the text with any replacements processed, <code>null</code> if
     * null String input
     */
    public static String replaceOnce(String text, String searchString, String replacement) {
        return replace(text, searchString, replacement, 1);
    }

    /**
     * <p>
     * Replaces all occurrences of a String within another String.</p>
     *
     * <p>
     * A <code>null</code> reference passed to this method is a no-op.</p>
     *
     * <pre>
     * StringUtils.replace(null, *, *)        = null
     * StringUtils.replace("", *, *)          = ""
     * StringUtils.replace("any", null, *)    = "any"
     * StringUtils.replace("any", *, null)    = "any"
     * StringUtils.replace("any", "", *)      = "any"
     * StringUtils.replace("aba", "a", null)  = "aba"
     * StringUtils.replace("aba", "a", "")    = "b"
     * StringUtils.replace("aba", "a", "z")   = "zbz"
     * </pre>
     *
     * @see #replace(String text, String searchString, String replacement, int
     * max)
     * @param text text to search and replace in, may be null
     * @param searchString the String to search for, may be null
     * @param replacement the String to replace it with, may be null
     * @return the text with any replacements processed, <code>null</code> if
     * null String input
     */
    public static String replace(String text, String searchString, String replacement) {
        return replace(text, searchString, replacement, -1);
    }

    /**
     * <p>
     * Replaces a String with another String inside a larger String, for the
     * first <code>max</code> values of the search String.</p>
     *
     * <p>
     * A <code>null</code> reference passed to this method is a no-op.</p>
     *
     * <pre>
     * StringUtils.replace(null, *, *, *)         = null
     * StringUtils.replace("", *, *, *)           = ""
     * StringUtils.replace("any", null, *, *)     = "any"
     * StringUtils.replace("any", *, null, *)     = "any"
     * StringUtils.replace("any", "", *, *)       = "any"
     * StringUtils.replace("any", *, *, 0)        = "any"
     * StringUtils.replace("abaa", "a", null, -1) = "abaa"
     * StringUtils.replace("abaa", "a", "", -1)   = "b"
     * StringUtils.replace("abaa", "a", "z", 0)   = "abaa"
     * StringUtils.replace("abaa", "a", "z", 1)   = "zbaa"
     * StringUtils.replace("abaa", "a", "z", 2)   = "zbza"
     * StringUtils.replace("abaa", "a", "z", -1)  = "zbzz"
     * </pre>
     *
     * @param text text to search and replace in, may be null
     * @param searchString the String to search for, may be null
     * @param replacement the String to replace it with, may be null
     * @param max maximum number of values to replace, or <code>-1</code> if no
     * maximum
     * @return the text with any replacements processed, <code>null</code> if
     * null String input
     */
    public static String replace(String text, String searchString, String replacement, int max) {
        if (isEmpty(text) || isEmpty(searchString) || replacement == null || max == 0) {
            return text;
        }
        int start = 0;
        int end = text.indexOf(searchString, start);
        if (end == -1) {
            return text;
        }
        int replLength = searchString.length();
        int increase = replacement.length() - replLength;
        increase = (increase < 0 ? 0 : increase);
        increase *= (max < 0 ? 16 : (max > 64 ? 64 : max));
        StringBuilder buf = new StringBuilder(text.length() + increase);
        while (end != -1) {
            buf.append(text.substring(start, end)).append(replacement);
            start = end + replLength;
            if (--max == 0) {
                break;
            }
            end = text.indexOf(searchString, start);
        }
        buf.append(text.substring(start));
        return buf.toString();
    }

    /**
     * <p>
     * Left pad a String with spaces (' ').</p>
     *
     * <p>
     * The String is padded to the size of <code>size</code>.</p>
     *
     * <pre>
     * StringUtils.leftPad(null, *)   = null
     * StringUtils.leftPad("", 3)     = "   "
     * StringUtils.leftPad("bat", 3)  = "bat"
     * StringUtils.leftPad("bat", 5)  = "  bat"
     * StringUtils.leftPad("bat", 1)  = "bat"
     * StringUtils.leftPad("bat", -1) = "bat"
     * </pre>
     *
     * @param str the String to pad out, may be null
     * @param size the size to pad to
     * @return left padded String or original String if no padding is necessary,
     * <code>null</code> if null String input
     */
    public static String leftPad(String str, int size) {
        return leftPad(str, size, ' ');
    }

    /**
     * <p>
     * Left pad a String with a specified character.</p>
     *
     * <p>
     * Pad to a size of <code>size</code>.</p>
     *
     * <pre>
     * StringUtils.leftPad(null, *, *)     = null
     * StringUtils.leftPad("", 3, 'z')     = "zzz"
     * StringUtils.leftPad("bat", 3, 'z')  = "bat"
     * StringUtils.leftPad("bat", 5, 'z')  = "zzbat"
     * StringUtils.leftPad("bat", 1, 'z')  = "bat"
     * StringUtils.leftPad("bat", -1, 'z') = "bat"
     * </pre>
     *
     * @param str the String to pad out, may be null
     * @param size the size to pad to
     * @param padChar the character to pad with
     * @return left padded String or original String if no padding is necessary,
     * <code>null</code> if null String input
     * @since 2.0
     */
    public static String leftPad(String str, int size, char padChar) {
        if (str == null) {
            return null;
        }
        int pads = size - str.length();
        if (pads <= 0) {
            return str; // returns original String when possible
        }
        if (pads > PAD_LIMIT) {
            return leftPad(str, size, String.valueOf(padChar));
        }
        return padding(pads, padChar).concat(str);
    }

    /**
     * <p>
     * Left pad a String with a specified String.</p>
     *
     * <p>
     * Pad to a size of <code>size</code>.</p>
     *
     * <pre>
     * StringUtils.leftPad(null, *, *)      = null
     * StringUtils.leftPad("", 3, "z")      = "zzz"
     * StringUtils.leftPad("bat", 3, "yz")  = "bat"
     * StringUtils.leftPad("bat", 5, "yz")  = "yzbat"
     * StringUtils.leftPad("bat", 8, "yz")  = "yzyzybat"
     * StringUtils.leftPad("bat", 1, "yz")  = "bat"
     * StringUtils.leftPad("bat", -1, "yz") = "bat"
     * StringUtils.leftPad("bat", 5, null)  = "  bat"
     * StringUtils.leftPad("bat", 5, "")    = "  bat"
     * </pre>
     *
     * @param str the String to pad out, may be null
     * @param size the size to pad to
     * @param padStr the String to pad with, null or empty treated as single
     * space
     * @return left padded String or original String if no padding is necessary,
     * <code>null</code> if null String input
     */
    public static String leftPad(String str, int size, String padStr) {
        if (str == null) {
            return null;
        }
        if (isEmpty(padStr)) {
            padStr = " ";
        }
        int padLen = padStr.length();
        int strLen = str.length();
        int pads = size - strLen;
        if (pads <= 0) {
            return str; // returns original String when possible
        }
        if (padLen == 1 && pads <= PAD_LIMIT) {
            return leftPad(str, size, padStr.charAt(0));
        }

        if (pads == padLen) {
            return padStr.concat(str);
        } else if (pads < padLen) {
            return padStr.substring(0, pads).concat(str);
        } else {
            char[] padding = new char[pads];
            char[] padChars = padStr.toCharArray();
            for (int i = 0; i < pads; i++) {
                padding[i] = padChars[i % padLen];
            }
            return new String(padding).concat(str);
        }
    }

    /**
     * <p>
     * Returns padding using the specified delimiter repeated to a given
     * length.</p>
     *
     * <pre>
     * StringUtils.padding(0, 'e')  = ""
     * StringUtils.padding(3, 'e')  = "eee"
     * StringUtils.padding(-2, 'e') = IndexOutOfBoundsException
     * </pre>
     *
     * <p>
     * Note: this method doesn't not support padding with
     * <a href="http://www.unicode.org/glossary/#supplementary_character">Unicode
     * Supplementary Characters</a>
     * as they require a pair of <code>char</code>s to be represented. If you
     * are needing to support full I18N of your applications consider using
     * {@link #repeat(String, int)} instead.
     * </p>
     *
     * @param repeat number of times to repeat delim
     * @param padChar character to repeat
     * @return String with repeated character
     * @throws IndexOutOfBoundsException if <code>repeat &lt; 0</code>
     * @see #repeat(String, int)
     */
    private static String padding(int repeat, char padChar) throws IndexOutOfBoundsException {
        if (repeat < 0) {
            throw new IndexOutOfBoundsException("Cannot pad a negative amount: " + repeat);
        }
        final char[] buf = new char[repeat];
        for (int i = 0; i < buf.length; i++) {
            buf[i] = padChar;
        }
        return new String(buf);
    }

    // Padding
    //-----------------------------------------------------------------------
    /**
     * <p>
     * Repeat a String <code>repeat</code> times to form a new String.</p>
     *
     * <pre>
     * StringUtils.repeat(null, 2) = null
     * StringUtils.repeat("", 0)   = ""
     * StringUtils.repeat("", 2)   = ""
     * StringUtils.repeat("a", 3)  = "aaa"
     * StringUtils.repeat("ab", 2) = "abab"
     * StringUtils.repeat("a", -2) = ""
     * </pre>
     *
     * @param str the String to repeat, may be null
     * @param repeat number of times to repeat str, negative treated as zero
     * @return a new String consisting of the original String repeated,
     * <code>null</code> if null String input
     * @since 2.5
     */
    public static String repeat(String str, int repeat) {
        // Performance tuned for 2.0 (JDK1.4)

        if (str == null) {
            return null;
        }
        if (repeat <= 0) {
            return EMPTY;
        }
        int inputLength = str.length();
        if (repeat == 1 || inputLength == 0) {
            return str;
        }
        if (inputLength == 1 && repeat <= PAD_LIMIT) {
            return padding(repeat, str.charAt(0));
        }

        int outputLength = inputLength * repeat;
        switch (inputLength) {
            case 1:
                char ch = str.charAt(0);
                char[] output1 = new char[outputLength];
                for (int i = repeat - 1; i >= 0; i--) {
                    output1[i] = ch;
                }
                return new String(output1);
            case 2:
                char ch0 = str.charAt(0);
                char ch1 = str.charAt(1);
                char[] output2 = new char[outputLength];
                for (int i = repeat * 2 - 2; i >= 0; i--, i--) {
                    output2[i] = ch0;
                    output2[i + 1] = ch1;
                }
                return new String(output2);
            default:
                StringBuilder buf = new StringBuilder(outputLength);
                for (int i = 0; i < repeat; i++) {
                    buf.append(str);
                }
                return buf.toString();
        }
    }

    // Count matches
    //-----------------------------------------------------------------------
    /**
     * <p>
     * Counts how many times the substring appears in the larger String.</p>
     *
     * <p>
     * A <code>null</code> or empty ("") String input returns
     * <code>0</code>.</p>
     *
     * <pre>
     * StringUtils.countMatches(null, *)       = 0
     * StringUtils.countMatches("", *)         = 0
     * StringUtils.countMatches("abba", null)  = 0
     * StringUtils.countMatches("abba", "")    = 0
     * StringUtils.countMatches("abba", "a")   = 2
     * StringUtils.countMatches("abba", "ab")  = 1
     * StringUtils.countMatches("abba", "xxx") = 0
     * </pre>
     *
     * @param str the String to check, may be null
     * @param sub the substring to count, may be null
     * @return the number of occurrences, 0 if either String is
     * <code>null</code>
     */
    public static int countMatches(String str, String sub) {
        if (isEmpty(str) || isEmpty(sub)) {
            return 0;
        }
        int count = 0;
        int idx = 0;
        while ((idx = str.indexOf(sub, idx)) != -1) {
            count++;
            idx += sub.length();
        }
        return count;
    }

    // Character Tests
    //-----------------------------------------------------------------------
    /**
     * <p>
     * Checks if the String contains only unicode letters.</p>
     *
     * <p>
     * <code>null</code> will return <code>false</code>. An empty String ("")
     * will return <code>true</code>.</p>
     *
     * <pre>
     * StringUtils.isAlpha(null)   = false
     * StringUtils.isAlpha("")     = true
     * StringUtils.isAlpha("  ")   = false
     * StringUtils.isAlpha("abc")  = true
     * StringUtils.isAlpha("ab2c") = false
     * StringUtils.isAlpha("ab-c") = false
     * </pre>
     *
     * @param str the String to check, may be null
     * @return <code>true</code> if only contains letters, and is non-null
     */
    public static boolean isAlpha(String str) {
        if (str == null) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if (Character.isLetter(str.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>
     * Checks if the String contains only unicode letters and space (' ').</p>
     *
     * <p>
     * <code>null</code> will return <code>false</code> An empty String ("")
     * will return <code>true</code>.</p>
     *
     * <pre>
     * StringUtils.isAlphaSpace(null)   = false
     * StringUtils.isAlphaSpace("")     = true
     * StringUtils.isAlphaSpace("  ")   = true
     * StringUtils.isAlphaSpace("abc")  = true
     * StringUtils.isAlphaSpace("ab c") = true
     * StringUtils.isAlphaSpace("ab2c") = false
     * StringUtils.isAlphaSpace("ab-c") = false
     * </pre>
     *
     * @param str the String to check, may be null
     * @return <code>true</code> if only contains letters and space, and is
     * non-null
     */
    public static boolean isAlphaSpace(String str) {
        if (str == null) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if ((Character.isLetter(str.charAt(i)) == false) && (str.charAt(i) != ' ')) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>
     * Checks if the String contains only unicode letters or digits.</p>
     *
     * <p>
     * <code>null</code> will return <code>false</code>. An empty String ("")
     * will return <code>true</code>.</p>
     *
     * <pre>
     * StringUtils.isAlphanumeric(null)   = false
     * StringUtils.isAlphanumeric("")     = true
     * StringUtils.isAlphanumeric("  ")   = false
     * StringUtils.isAlphanumeric("abc")  = true
     * StringUtils.isAlphanumeric("ab c") = false
     * StringUtils.isAlphanumeric("ab2c") = true
     * StringUtils.isAlphanumeric("ab-c") = false
     * </pre>
     *
     * @param str the String to check, may be null
     * @return <code>true</code> if only contains letters or digits, and is
     * non-null
     */
    public static boolean isAlphanumeric(String str) {
        if (str == null) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if (Character.isLetterOrDigit(str.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>
     * Checks if the String contains only unicode letters, digits or space
     * (<code>' '</code>).</p>
     *
     * <p>
     * <code>null</code> will return <code>false</code>. An empty String ("")
     * will return <code>true</code>.</p>
     *
     * <pre>
     * StringUtils.isAlphanumeric(null)   = false
     * StringUtils.isAlphanumeric("")     = true
     * StringUtils.isAlphanumeric("  ")   = true
     * StringUtils.isAlphanumeric("abc")  = true
     * StringUtils.isAlphanumeric("ab c") = true
     * StringUtils.isAlphanumeric("ab2c") = true
     * StringUtils.isAlphanumeric("ab-c") = false
     * </pre>
     *
     * @param str the String to check, may be null
     * @return <code>true</code> if only contains letters, digits or space, and
     * is non-null
     */
    public static boolean isAlphanumericSpace(String str) {
        if (str == null) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if ((Character.isLetterOrDigit(str.charAt(i)) == false) && (str.charAt(i) != ' ')) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>
     * Checks if the String contains only unicode digits. A decimal point is not
     * a unicode digit and returns false.</p>
     *
     * <p>
     * <code>null</code> will return <code>false</code>. An empty String ("")
     * will return <code>true</code>.</p>
     *
     * <pre>
     * StringUtils.isNumeric(null)   = false
     * StringUtils.isNumeric("")     = false
     * StringUtils.isNumeric("  ")   = false
     * StringUtils.isNumeric("123")  = true
     * StringUtils.isNumeric("12 3") = false
     * StringUtils.isNumeric("ab2c") = false
     * StringUtils.isNumeric("12-3") = false
     * StringUtils.isNumeric("12.3") = true
     * </pre>
     *
     * @param str the String to check, may be null
     * @return <code>true</code> if only contains digits, and is non-null
     */
    public static boolean isNumeric(String str) {
        if (str == null || str.length() <= 0) {
            return false;
        }
        String reg = "([+-]{0,1}0\\.\\d+)|([+-]{0,1}[0-9]\\d*\\.\\d+)|([+-]{0,1}[0-9]\\d*)|([+-]{0,1}0)";
        return str.matches(reg);
    }

    /**
     * <p>
     * Checks if the String contains only unicode digits or space
     * (<code>' '</code>). A decimal point is not a unicode digit and returns
     * false.</p>
     *
     * <p>
     * <code>null</code> will return <code>false</code>. An empty String ("")
     * will return <code>true</code>.</p>
     *
     * <pre>
     * StringUtils.isNumeric(null)   = false
     * StringUtils.isNumeric("")     = true
     * StringUtils.isNumeric("  ")   = true
     * StringUtils.isNumeric("123")  = true
     * StringUtils.isNumeric("12 3") = true
     * StringUtils.isNumeric("ab2c") = false
     * StringUtils.isNumeric("12-3") = false
     * StringUtils.isNumeric("12.3") = true
     * </pre>
     *
     * @param str the String to check, may be null
     * @return <code>true</code> if only contains digits or space, and is
     * non-null
     */
    public static boolean isNumericSpace(String str) {
        if (str == null) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if ((Character.isDigit(str.charAt(i)) == false) && (str.charAt(i) != ' ')) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>
     * Checks if the String contains only whitespace.</p>
     *
     * <p>
     * <code>null</code> will return <code>false</code>. An empty String ("")
     * will return <code>true</code>.</p>
     *
     * <pre>
     * StringUtils.isWhitespace(null)   = false
     * StringUtils.isWhitespace("")     = true
     * StringUtils.isWhitespace("  ")   = true
     * StringUtils.isWhitespace("abc")  = false
     * StringUtils.isWhitespace("ab2c") = false
     * StringUtils.isWhitespace("ab-c") = false
     * </pre>
     *
     * @param str the String to check, may be null
     * @return <code>true</code> if only contains whitespace, and is non-null
     * @since 2.0
     */
    public static boolean isWhitespace(String str) {
        if (str == null) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>
     * Checks if the String contains only lowercase characters.</p>
     *
     * <p>
     * <code>null</code> will return <code>false</code>. An empty String ("")
     * will return <code>false</code>.</p>
     *
     * <pre>
     * StringUtils.isAllLowerCase(null)   = false
     * StringUtils.isAllLowerCase("")     = false
     * StringUtils.isAllLowerCase("  ")   = false
     * StringUtils.isAllLowerCase("abc")  = true
     * StringUtils.isAllLowerCase("abC") = false
     * </pre>
     *
     * @param str the String to check, may be null
     * @return <code>true</code> if only contains lowercase characters, and is
     * non-null
     * @since 2.5
     */
    public static boolean isAllLowerCase(String str) {
        if (str == null || isEmpty(str)) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if (Character.isLowerCase(str.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>
     * Checks if the String contains only uppercase characters.</p>
     *
     * <p>
     * <code>null</code> will return <code>false</code>. An empty String ("")
     * will return <code>false</code>.</p>
     *
     * <pre>
     * StringUtils.isAllUpperCase(null)   = false
     * StringUtils.isAllUpperCase("")     = false
     * StringUtils.isAllUpperCase("  ")   = false
     * StringUtils.isAllUpperCase("ABC")  = true
     * StringUtils.isAllUpperCase("aBC") = false
     * </pre>
     *
     * @param str the String to check, may be null
     * @return <code>true</code> if only contains uppercase characters, and is
     * non-null
     * @since 2.5
     */
    public static boolean isAllUpperCase(String str) {
        if (str == null || isEmpty(str)) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if (Character.isUpperCase(str.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    // Reversing
    //-----------------------------------------------------------------------
    /**
     * <p>
     * Reverses a String as per {@link StringBuffer#reverse()}.</p>
     *
     * <p>
     * A <code>null</code> String returns <code>null</code>.</p>
     *
     * <pre>
     * StringUtils.reverse(null)  = null
     * StringUtils.reverse("")    = ""
     * StringUtils.reverse("bat") = "tab"
     * </pre>
     *
     * @param str the String to reverse, may be null
     * @return the reversed String, <code>null</code> if null String input
     */
    public static String reverse(String str) {
        if (str == null) {
            return null;
        }
        return new StringBuffer(str).reverse().toString();
    }

    /**
     * 处理下载文件文件名问题
     *
     * @param filename
     * @param request
     * @return
     */
    public static String encodeFilename(String filename, HttpServletRequest request) {
        /**
         * 获取客户端浏览器和操作系统信息 在IE浏览器中得到的是：User-Agent=Mozilla/4.0 (compatible; MSIE
         * 6.0; Windows NT 5.1; SV1; Maxthon; Alexa Toolbar)
         * 在Firefox中得到的是：User-Agent=Mozilla/5.0 (Windows; U; Windows NT 5.1;
         * zh-CN; rv:1.7.10) Gecko/20050717 Firefox/1.0.6
         */
        String agent = request.getHeader("USER-AGENT");
        try {
            if (agent != null && (agent.contains("MSIE") || agent.contains("Edge") || agent.contains("Trident"))) {
                String newFileName = URLEncoder.encode(filename, "UTF-8");
                newFileName = replace(newFileName, "+", "%20");
                if (newFileName.length() > 150) {
                    newFileName = new String(filename.getBytes("UTF-8"), "ISO8859-1");
                    newFileName = replace(newFileName, " ", "%20");
                }
                return newFileName;
            }
            if (agent != null && agent.contains("Mozilla")) {
                //return MimeUtility.encodeText(newFileName, "UTF-8", "B");
                return replace(new String(filename.getBytes("UTF-8"), "iso-8859-1"), " ", "_");
            }
            return filename;
        } catch (UnsupportedEncodingException ex) {
            return filename;
        }
    }

    public static String formatString(String str, int len, char ch) {
        return formatFixedString(str, len, ch);
    }

    /**
     * 将byte类型数据转换为16进制字符串
     *
     * @param b
     * @return
     */
    public static String byte2HexString(byte b) {
        return formatFixedString(Long.toHexString(b & 0xFF).toUpperCase(), 2, '0');
    }

    /**
     * 将byte数组类型数据转换为16进制字符串(带空格)
     *
     * @param bytes 字节数组
     * @param decollator 分隔符
     * @return
     */
    public static String bytes2HexString(byte[] bytes, String decollator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            if (i < bytes.length - 1) {
                decollator = decollator == null ? "" : decollator;
                sb.append(formatFixedString(Long.toHexString(bytes[i] & 0xFF).toUpperCase(), 2, '0')).append(decollator);
            } else {
                sb.append(formatFixedString(Long.toHexString(bytes[i] & 0xFF).toUpperCase(), 2, '0'));
            }
        }
        return sb.toString();
    }

    /**
     * 将byte数组类型数据转换为16进制字符串(带空格)
     *
     * @param bytes
     * @return
     */
    public static String bytes2HexString(byte[] bytes) {
        return bytes2HexString(bytes, " ");
    }

    /**
     * 将byte数组类型数据转换为16进制字符串(不带空格)
     *
     * @param bytes
     * @return
     */
    public static String bytes2HexStringNS(byte[] bytes) {
        return bytes2HexString(bytes, "");
    }

    /**
     * @Title:hexString2Bytes
     * @Description:16进制字符串转字节数组
     * @param hexStr 16进制字符串
     * @return 字节数组
     */
    public static byte[] hexString2Bytes(String hexStr) {

        hexStr = hexStr.trim().replace(" ", "").replace(":", "");

        if (hexStr.length() % 2 != 0) { //字符数不为偶数，在前面添加0
            hexStr = "0" + hexStr;
        }

        int len = hexStr.length() / 2;
        byte[] ret = new byte[len];
        for (int i = 0; i < len; i++) {
            ret[i] = Integer.valueOf(hexStr.substring(i * 2, i * 2 + 2), 16).byteValue();
        }
        return ret;
    }

    /**
     * @Title:string2HexString
     * @Description:字符串转16进制字符串
     * @param strPart 字符串
     * @return 16进制字符串
     */
    public static String string2HexString(String strPart) {
        return bytes2HexString(strPart.getBytes());
    }

    /**
     * @Title:hexString2String
     * @Description:16进制字符串转字符串
     * @param hexStr 16进制字符串
     * @return 字节数组
     */
    public static String hexString2String(String hexStr) {
        return new String(hexString2Bytes(hexStr));
    }

    /**
     * byte类型数组转换二进制字符串
     *
     * @param bs
     * @return
     */
    public static String bytes2BinaryString(byte[] bs) {
        StringBuilder sb = new StringBuilder();

        String ZERO = "00000000";
        for (int i = 0; i < bs.length; i++) {
            String s = Integer.toBinaryString(bs[i]);
            if (s.length() > 8) {
                s = s.substring(s.length() - 8);
            } else if (s.length() < 8) {
                s = ZERO.substring(s.length()) + s;
            }
            sb.append(s).append(" ");
        }
        return sb.toString();
    }

    /**
     * byte类型数组转换二进制字符串
     *
     * @param binaryString
     * @return
     */
    public static byte[] binaryString2Bytes(String binaryString) {
        binaryString = binaryString.replace(" ", "");

        byte[] bytes;
        if (binaryString.length() % 8 != 0) {
            binaryString = "0" + binaryString;
        }
        bytes = new byte[binaryString.length() / 8];

        for (int i = 0; i < binaryString.length(); i += 8) {
            //System.out.println(binaryString.substring(i, i+4));
            bytes[i / 8] = (byte) Short.parseShort(binaryString.substring(i, i + 8), 2);
        }

        return bytes;
    }

    /**
     * 将int类型数据格式化成指定长度的10进制字符串
     *
     * @param num
     * @param len
     * @return
     */
    public static String int2FixedDec(int num, int len) {
        return StringUtils.formatFixedString(String.valueOf(num), len, '0');
    }

    /**
     * 将10进制的int类型数据格式化成指定长度的16进制字符串
     *
     * @param num
     * @param len
     * @return
     */
    public static String int2FixedHex(int num, int len) {
        return StringUtils.formatFixedString(Integer.toHexString(num), len, '0');
    }
/**
     * 将long类型数据格式化成指定长度的10进制字符串
     *
     * @param num
     * @param len
     * @return
     */
    public static String long2FixedDec(long num, int len) {
        return StringUtils.formatFixedString(String.valueOf(num), len, '0');
    }

    /**
     * 将10进制的long类型数据格式化成指定长度的16进制字符串
     *
     * @param num
     * @param len
     * @return
     */
    public static String long2FixedHex(long num, int len) {
        return StringUtils.formatFixedString(Long.toHexString(num), len, '0');
    }
    /**
     * 将字符串转换为指定长度的字节数组
     *
     * @param str
     * @param len
     * @return
     */
    public static byte[] String2FixedBytes(String str, int len) {
        return bytes2FixedBytes(str.getBytes(), len);
    }

    /**
     * 将字节数组转换为指定长度的字节数组
     *
     * @param resultBytes
     * @param len
     * @return
     */
    public static byte[] bytes2FixedBytes(byte[] resultBytes, int len) {
        byte[] bytes = new byte[len];
        for (int i = 0; i < resultBytes.length; i++) {
            bytes[i] = resultBytes[i];
        }
        return bytes;
    }

    /**
     * 将字符串格式化成指定长度和指定填充内容的字符串
     *
     * @param str 要处理的字符串
     * @param len 要格式化的长度
     * @param c 要填充的字符
     * @return
     */
    public static String formatFixedString(String str, int len, char c) {
        if (str == null || str.length() == 0) {
            str = "";
        }
        if (str.length() == len) {
            return str;
        }
        if (str.length() > len) {
            return str.substring(0, len);
        }
        StringBuilder sb = new StringBuilder();
        while (str.length() + sb.length() < len) {
            sb.append(c);
        }
        sb.append(str);
        return sb.toString();
    }

    /**
     * 将参数由short型转换为identifier String类型
     *
     * @param paramID
     * @return
     */
    public static String getStringIdentifier(Short paramID) {

        String identifier = Integer.toHexString(paramID & 0xFFFF).toUpperCase();

        switch (identifier.length()) {
            case 1:
            case 5:
                identifier = "0x000" + identifier;
                break;
            case 2:
            case 6:
                identifier = "0x00" + identifier;
                break;
            case 3:
            case 7:
                identifier = "0x0" + identifier;
                break;
            case 4:
            case 8:
                identifier = "0x" + identifier;
                break;
            default:
                break;
        }
        return identifier;
    }

    /**
     * 将参数由short型转换为identifier String类型
     *
     * @param paramID
     * @return
     */
    public static String getStringIdentifier(Integer paramID) {

        String identifier = Long.toHexString(paramID & 0xFFFFFFFFL).toUpperCase();
        switch (identifier.length()) {
            case 1:
            case 5:
                identifier = "0x000" + identifier;
                break;
            case 2:
            case 6:
                identifier = "0x00" + identifier;
                break;
            case 3:
            case 7:
                identifier = "0x0" + identifier;
                break;
            case 4:
            case 8:
                identifier = "0x" + identifier;
                break;
            default:
                break;
        }
        return identifier;
    }

    /**
     * 过滤sql查询语句类似like判断条件字符串中的特殊字符处理
     *
     * @param sql
     * @return
     */
    public static String filterLikeSql(String sql) {
        if (null != sql) {
            String newValue;
            newValue = sql.replaceAll("\\\\", "\\\\\\\\");
            newValue = newValue.replaceAll("'", "\\\\'");
            newValue = newValue.replaceAll("_", "\\\\_");
            newValue = newValue.replaceAll("\"", "\\\\\"");
            newValue = newValue.replaceAll("%", "\\\\%");
            return newValue;
        }
        return sql;
    }

    /**
     * 过滤sql查询语句除了like判断条件字符串中的特殊字符处理
     *
     * @param value sql字符串
     * @return
     */
    public static String filterNotLikeSql(String value) {
        if (null != value) {
            return value.replaceAll("\\\\", "\\\\\\\\").replaceAll("\"", "\\\\\"");
        }
        return value;
    }

    /**
     * 创建当前分区名称
     *
     * @return
     */
    public static String getPartName() {
        return getPartName(new Date());
    }

    /**
     * 创建当前分区名称
     *
     * @param date 时间日期
     * @return
     */
    public static String getPartName(Date date) {
        return "p" + DateUtils.formatDateTime(date, "yyyMMdd");
    }

    /**
     * 判断字符串是否包含汉字
     *
     * @param str
     * @return
     */
    public static boolean isIncludeChineseChar(String str) {
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (Character.isLetter(c) && Character.getType(c) == 5) {
                return true;
            }
        }
        return false;
    }
    /**
     * 获取标准十六进制数
     * @param intValue
     * @return
     */
    public static String getStandarHexStr(byte bValue){
        return String.format("%02x", new Integer(bValue & 0xFF));
    }
    
}