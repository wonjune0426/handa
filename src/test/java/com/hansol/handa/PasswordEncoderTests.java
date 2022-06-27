package com.hansol.handa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class PasswordEncoderTests {

    @Setter(onMethod_ = {@Autowired})
    private BCryptPasswordEncoder pwEncoder;

    @Test
    public void testEncode() {
        String str = "dl7542";
        String enStr = pwEncoder.encode(str);
        
        log.info("test----------------------------------------------------------------");

        // dl7542: $2a$10$h105Nqwm6h/DHqhG6auwhuwyoiZ13VZ00ib50XBMjms6kBe9xX/Pe
        log.info(enStr);
    }

    
}
