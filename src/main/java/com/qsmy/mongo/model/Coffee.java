package com.qsmy.mongo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.money.Money;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @author qsmy
 * @date 2019-07-22
 */
@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Coffee {

    @Id
    private String id;
    private String name;
    private Money price;
    private Date createTime;
    private Date updateTime;
}
