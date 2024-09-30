package com.jpabook.jpashop.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.FetchType.LAZY;


@Entity
@Setter
@Getter
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deliver_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = LAZY)
    private Orders order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING )
    private DeliveryStatus status; //READY,COMP
}
