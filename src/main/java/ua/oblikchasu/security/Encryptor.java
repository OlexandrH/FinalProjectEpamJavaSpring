package ua.oblikchasu.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Encryptor {
    private static final BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder(10);

    public static String hash(String password) {
        return bcrypt.encode(password);
    }
}
