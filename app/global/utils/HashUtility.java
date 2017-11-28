package global.utils;

import org.mindrot.jbcrypt.BCrypt;

final public class HashUtility {

    public static String hash(String value) {
        return BCrypt.hashpw(value, BCrypt.gensalt());
    }

    public static boolean compare(String password, String hashed) {
        return BCrypt.checkpw(password,hashed);
    }

    public static long currentEpoch() {
        return System.currentTimeMillis()/1000;
    }
}
