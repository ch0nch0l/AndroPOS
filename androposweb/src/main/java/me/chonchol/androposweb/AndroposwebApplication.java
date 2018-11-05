package me.chonchol.androposweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

//@ComponentScan("me.chonchol.androposweb.entity.report")
@SpringBootApplication
public class AndroposwebApplication {

    public static void main(String[] args) {
        SpringApplication.run(AndroposwebApplication.class, args);
    }
}
