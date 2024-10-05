package com.jpabook.jpashop.service;

import com.jpabook.jpashop.domain.Address;
import com.jpabook.jpashop.domain.Member;
import com.jpabook.jpashop.domain.OrderStatus;
import com.jpabook.jpashop.domain.Orders;
import com.jpabook.jpashop.domain.item.Book;
import com.jpabook.jpashop.repository.OrdersRepository;
import jakarta.persistence.EntityManager;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired EntityManager em;
    @Autowired OrderService orderService;
    @Autowired OrdersRepository ordersRepository;

    @Test
    public void 상품주문() throws Exception{
        //given
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울","금정구","220"));
        em.persist(member);



        Book book = new Book();
        book.setName("시골책");
        book.setPrice(10000);
        book.setStockQuantity(10);
        em.persist(book);



        int orderCount = 2;
        //when
        Long orderId = orderService.orders(member.getId(), book.getId(), orderCount);


        //then
        Orders getOrder = ordersRepository.findOne(orderId);

        assertEquals("상품 주무신 상태는 ORDER", OrderStatus.order, getOrder.getOrderStatus());
        assertEquals("주문한 상품 종류가 수가 정확해야 한다.", 1, getOrder.getOrderItems().size());
        assertEquals("주문 가격은 가격 * 수량이다.", 10000 * orderCount, getOrder.getTotalPrice());
        assertEquals("주문 수량만큼 재고가 줄어야 한다." , 8, book.getStockQuantity());

    }



    @Test
    public void 주문취소() throws  Exception{
        //given


        //when


        //then
    }


    @Test
    public void 상품주문_재고수량초과() throws  Exception{
        //given


        //when


        //then
    }


}