package xyz.nimeide.holiday;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * @author wangjian
 */
@SpringBootApplication
@EnableR2dbcRepositories
@EnableJpaRepositories
@EnableTransactionManagement
public class HolidayApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(HolidayApiApplication.class, args);
    }

}
