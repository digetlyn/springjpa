package com.jpabook.jpashop.domain;


import com.jpabook.jpashop.domain.item.Item;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.FetchType.LAZY;


@Entity
@Getter
@Setter
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;


    @ManyToOne (fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne (fetch = LAZY)
    @JoinColumn(name = "order_id ")
    private Orders order;

    private int orderPrice; //주문 가격
    private int count;   //주문 수량



}
