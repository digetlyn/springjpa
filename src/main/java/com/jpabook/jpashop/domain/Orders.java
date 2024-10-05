package com.jpabook.jpashop.domain;

import jakarta.persistence.*;
import jakarta.persistence.criteria.Order;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@Table(name = "Orders")
public class  Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne (fetch = LAZY) //member랑 다 대 일 관계
    @JoinColumn(name = "member_id")
    private Member member;  //주문회원

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne (fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery; //배송정보

    private LocalDateTime orderDate;  //주문시간

    @Enumerated(EnumType.STRING )
    private OrderStatus orderStatus;   //주문상태 [order, cancel]

    //연관 관계 매서드//

    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);
    }


    //==생성 메서드==//

    public static Orders createOrders(Member member, Delivery delivery, OrderItem... orderItems){
        Orders orders = new Orders();
        orders.setMember(member);
        orders.setDelivery(delivery);
        for(OrderItem orderItem : orderItems) {
            orders.addOrderItem(orderItem);
        }
        orders.setOrderStatus(OrderStatus.order);
       // orders.setStatus(OrderStatus.order);
        orders.setOrderDate(LocalDateTime.now());
        return orders;
    }


    // == 비즈니스 로직 == //
    /*
    * 주문취소
    * */

    public void cancel(){
        if (delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가합니다.");
        }

        this.setOrderStatus(OrderStatus.cancel);
        for (OrderItem orderItem : orderItems){
            orderItem.cancel();
        }
    }

    //==조회 로직==//

      /*
      * 전체 주문가격 조회
      * */
    public int getTotalPrice() {
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems){
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }



}
