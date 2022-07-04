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
        String str = "test";
        String enStr = pwEncoder.encode(str);
        
        log.info("test----------------------------------------------------------------");

        // goeunlee hyunjilee dl7542: $2a$10$h105Nqwm6h/DHqhG6auwhuwyoiZ13VZ00ib50XBMjms6kBe9xX/Pe
        // wonjune0426 dnjs7542 : $2a$10$tFoxHLR0yP3Ff.utec/w5Oy0rk/QGIyna0LXiBtRVBFl6g4B3TTGe
        // taeeunkim rla7542 : $2a$10$ops0Q8IwiAJK8UFgAGgO6uYqOAHqe4De9tCUQPYIYeJk7C.peF.Wi
        // hyeonjeong wjs7542 : $2a$10$fR7j.CG97i7nKC8DkB4E0ezuvJs9uOMxQJFL02gFE2WItn5eS9aiu
        // test : $2a$10$nM8KY7tWgFpUiN3E84T5ueEfOMfxecbS4CtPkZZn3JjwZTl22iUCy
        
        log.info(enStr);
    }

    
}
