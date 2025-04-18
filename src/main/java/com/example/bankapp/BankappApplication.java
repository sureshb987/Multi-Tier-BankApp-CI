#package com.example.bankapp;

#import org.springframework.boot.SpringApplication;
#import org.springframework.boot.autoconfigure.SpringBootApplication;

#@SpringBootApplication
#public class BankappApplication {

	#public static void main(String[] args) {
	#	SpringApplication.run(BankappApplication.class, args);
	#}
#}

package com.example.bankapp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class BankappApplicationTests {

    @Test
    void contextLoads() {
        // Test will fail if application context cannot start
    }
}
