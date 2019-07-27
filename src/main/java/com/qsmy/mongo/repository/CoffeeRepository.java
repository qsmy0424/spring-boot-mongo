package com.qsmy.mongo.repository;

import com.qsmy.mongo.model.Coffee;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @author qsmy
 * @date 2019-07-22
 */
public interface CoffeeRepository extends MongoRepository<Coffee, String> {
    List<Coffee>findByName(String name);
}
