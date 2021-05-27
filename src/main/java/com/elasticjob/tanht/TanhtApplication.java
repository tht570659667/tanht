package com.elasticjob.tanht;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication()
@ComponentScan(basePackages = "com.elasticjob.tanht.*")
@EnableTransactionManagement
//@MapperScan("com.elasticjob.tanht.mapper")
public class TanhtApplication {

    public static void main(String[] args) {
        SpringApplication.run(TanhtApplication.class, args);
    }

}
