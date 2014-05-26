package ums.spring.utils;

import org.springframework.util.Assert;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DigestUtils {

    private static char[] HEX_CHARS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    private DigestUtils() {
        // prevent instantiation
    }

    /**
     * Calculate the SHA1 hash for a given string.
     *
     * @param text the given text to hash to a SHA1
     * @return the SHA1 Hash
     */
    public static String sha1(String text) {
        Assert.notNull(text);
        try {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            return hex(md.digest(text.getBytes("UTF-8")));
        } catch (NoSuchAlgorithmException ex) {
            throw new IllegalStateException(
                    "Unable to calculate hash. No SHA1 hasher available in this Java implementation", ex);
        } catch (UnsupportedEncodingException ex) {
            throw new IllegalStateException(
                    "Unable to calculate hash. UTF-8 encoding is not available in this Java implementation", ex);
        }
    }

    /**
     * Converts the byte array to a string containing its Hex representation.
     *
     * @param data the byte array of data
     * @return the hex representation of the String
     */
    public static String hex(byte[] data) {
        Assert.notNull(data);
        return hex(data, 0, data.length);
    }

    /**
     * Converts the byte array to a string containing its Hex representation.
     *
     * @param data the byte array of data
     * @param offset An offset in the array where the encoding should start
     * @param length Indicates how many bytes should be encoded.
     * @return the hex representation of the String
     */
    public static String hex(byte[] data, int offset, int length) {
        Assert.notNull(data);
        Assert.state(offset >= 0, "The offset must be positive");
        Assert.state(offset < data.length, "The offset must be lower than the length of the data");
        Assert.state(length >= 0, "The requested length must be positive");
        Assert.state(length <= data.length,
                "The requested length must be equal to or lower than the length of the data");

        StringBuilder buf = new StringBuilder(length * 2);
        for (int i = offset; i < (offset + length); i++) {
            byte b = data[i];
            // look up high nibble char
            buf.append(HEX_CHARS[(b & 0xf0) >>> 4]);
            // look up low nibble char
            buf.append(HEX_CHARS[b & 0x0f]);
        }
        return buf.toString();
    }
}
