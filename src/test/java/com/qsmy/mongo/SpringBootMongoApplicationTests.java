package com.qsmy.mongo;

import com.mongodb.client.result.UpdateResult;
import com.qsmy.mongo.model.Coffee;
import com.qsmy.mongo.repository.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootMongoApplicationTests {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private CoffeeRepository coffeeRepository;

    @Test
    public void contextLoads() throws Exception {
        log.info("----------start----------");
        Coffee coffee = Coffee.builder()
                .name("qsmy")
                .price(Money.of(CurrencyUnit.of("CNY"), 20.0))
                .createTime(new Date())
                .createTime(new Date()).build();
        Coffee save = mongoTemplate.save(coffee);
        log.info("Coffee {}", save);

        List<Coffee> list = mongoTemplate.find(Query.query(Criteria.where("name").is("qsmy")), Coffee.class);
        log.info("Find {} Coffee", list.size());

        list.forEach(c -> log.info("Coffee {}", c));

        Thread.sleep(1000);
        UpdateResult result = mongoTemplate.updateFirst(Query.query(Criteria.where("name").is("qsmy")),
                new Update().set("price", Money.ofMajor(CurrencyUnit.of("CNY"), 30))
                        .currentDate("updateTime"), Coffee.class);

        log.info("Update Result: {}", result.getModifiedCount());
        Coffee updateOne = mongoTemplate.findById(save.getId(), Coffee.class);
        log.info("Update Result: {}", updateOne);

        Optional.ofNullable(updateOne).ifPresent(mongoTemplate::remove);
    }

    @Test
    public void testRepository() throws Exception {
        Coffee coffee1 = Coffee.builder()
                .name("qsmy1")
                .price(Money.of(CurrencyUnit.of("CNY"), 20.0))
                .createTime(new Date())
                .createTime(new Date()).build();
        Coffee coffee2 = Coffee.builder()
                .name("qsmy2")
                .price(Money.of(CurrencyUnit.of("CNY"), 30.0))
                .createTime(new Date())
                .createTime(new Date()).build();

        coffeeRepository.insert(Arrays.asList(coffee1, coffee2));
        coffeeRepository.findAll(Sort.by("name")).forEach(c -> log.info("Saved Coffee {}", c));

        Thread.sleep(1000);
        coffee2.setPrice(Money.of(CurrencyUnit.of("CNY"), 35.0));
        coffee2.setUpdateTime(new Date());
        coffeeRepository.save(coffee2);
        coffeeRepository.findByName("qsmy2").forEach(c -> log.info("Coffee {}", c));
        coffeeRepository.deleteAll();
    }

}
