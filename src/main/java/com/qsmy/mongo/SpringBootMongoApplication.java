package com.qsmy.mongo;

import com.qsmy.mongo.convert.MoneyReadConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Collections;

/**
 * @author qsmy
 */
@EnableMongoRepositories
@SpringBootApplication
@Slf4j
public class SpringBootMongoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootMongoApplication.class, args);
    }

    @Bean
    public MongoCustomConversions mongoCustomConversions() {
        return new MongoCustomConversions(Collections.singletonList(new MoneyReadConverter()));
    }
}
