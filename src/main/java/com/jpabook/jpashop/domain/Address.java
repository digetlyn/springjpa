package com.jpabook.jpashop.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class  Address {

    private String city;
    private String street;
    private String zipcode;

    protected Address(){

        // 이슈발행테스트

    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;

        // commit하기
    }
}
