package com.jpabook.jpashop.service;


import com.jpabook.jpashop.domain.Delivery;
import com.jpabook.jpashop.domain.Member;
import com.jpabook.jpashop.domain.OrderItem;
import com.jpabook.jpashop.domain.Orders;
import com.jpabook.jpashop.domain.item.Item;
import com.jpabook.jpashop.repository.ItemRepository;
import com.jpabook.jpashop.repository.MemberRepository;
import com.jpabook.jpashop.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrdersRepository ordersRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /*
    * 주문
    * */
    @Transactional
    public Long orders(Long memberId, Long itemId, int count) {

        //엔티티 조회
        Member member = memberRepository.findOne(memberId);//memberRepository에서  memberId찾는다
        Item item = itemRepository.findOne(itemId);  //itemRepository 에서 itemId 찾는다

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);


        //주문 생성
        Orders orders = Orders.createOrders(member, delivery, orderItem);


        //주문 저장
        ordersRepository.save(orders);
        return orders.getId();


    }

    /*
    * 주문 취소
    * */
    @Transactional
    public void cancelOrders(Long ordersId){
        //주문 엔티티 조회
        Orders orders = ordersRepository.findOne(ordersId);
        //주문 취소
        orders.cancel();
    }

    //검색

/*    public List<Orders> findOrders(OrdersSearch ordersSearch) {
        return  ordersRepository.findAll(ordersSearch);
    }

 */
}
