package dwz.common.util.encrypt;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

public class SpringEncryptUtil {
    // 从配置文件中获得
    private static final String SITE_WIDE_SECRET = "com.irongteng.ipms";
    private static PasswordEncoder encoder;

    static {
        encoder = new StandardPasswordEncoder(SITE_WIDE_SECRET);
    }
    
    public static String encrypt(String rawPassword) {
        return encrypt(SITE_WIDE_SECRET, rawPassword);
    }

    public static String encrypt(String username, String rawPassword) {
        encoder = new StandardPasswordEncoder(SITE_WIDE_SECRET + username);
        return encoder.encode(rawPassword).toUpperCase();
    }

    public static boolean match(String rawPassword, String password) {
        return match(SITE_WIDE_SECRET,rawPassword, password);
    }
    
    public static boolean match(String username, String rawPassword, String password) {
        encoder = new StandardPasswordEncoder(SITE_WIDE_SECRET + username);
        return encoder.matches(rawPassword, password);
    }
}