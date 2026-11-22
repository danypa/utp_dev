package pe.edu.magistra.util;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

public final class PasswordUtils {

    private static final int ITER = 120000, BITS = 256;

    private PasswordUtils() {
    }

    public static String hash(String password) {
        try {
            byte[] salt = new byte[16];
            new SecureRandom().nextBytes(salt);
            byte[] h = pbkdf(password.toCharArray(), salt, ITER);
            return ITER + ":" + Base64.getEncoder().encodeToString(salt) + ":" + Base64.getEncoder().encodeToString(h);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static boolean verify(String password, String stored) {
        try {
            String[] p = stored.split(":");
            int it = Integer.parseInt(p[0]);
            byte[] salt = Base64.getDecoder().decode(p[1]);
            byte[] exp = Base64.getDecoder().decode(p[2]);
            byte[] got = pbkdf(password.toCharArray(), salt, it);
            if (exp.length != got.length) {
                return false;
            }
            int d = 0;
            for (int i = 0; i < exp.length; i++) {
                d |= exp[i] ^ got[i];
            }
            return d == 0;
        } catch (Exception e) {
            return false;
        }
    }

    private static byte[] pbkdf(char[] pwd, byte[] salt, int it) throws Exception {
        PBEKeySpec spec = new PBEKeySpec(pwd, salt, it, BITS);
        return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded();
    }
}
