package org.mishpaha.project.test;

import org.junit.Test;
import org.mishpaha.project.config.SecurityConfig;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

public class UnitTests {

    @Test
    public void testEncoding() {
        int i = 0;
        while (i < 10) {
            String password = "psw";
            StandardPasswordEncoder passwordEncoder = new StandardPasswordEncoder(SecurityConfig.SECRET);
            String hashedPassword = passwordEncoder.encode(password);
            System.out.println(hashedPassword);
            i++;
        }
    }
}
