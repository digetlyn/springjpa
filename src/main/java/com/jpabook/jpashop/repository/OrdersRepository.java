package com.jpabook.jpashop.repository;


import com.jpabook.jpashop.domain.Orders;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrdersRepository {

    private final EntityManager em;

    public void save(Orders orders) { em.persist(orders);
    }

    public Orders findOne(Long id) { return em.find(Orders.class, id);
    }

     public List<Orders> findAll(OrdersSearch ordersSearch) {
        String jpql ="select o from Orders o join o.member m";
        boolean isFirstCondition = true;

        //주문상태검색
         if (ordersSearch.getOrderStatus() != null) {
             if (isFirstCondition) {
                 jpql += " where ";
                 isFirstCondition = false;
             } else {
                 jpql += " and ";
             }
             jpql += "o.status = :status";
         }

       //회원 이름 검색
         if (StringUtils.hasText(ordersSearch.getMemberName())) {
             if (isFirstCondition) {
                 jpql += "where";
                 isFirstCondition = false;
             } else {
                 jpql += " and ";
             }
             jpql += "m.name like :name";
         }
         TypedQuery<Orders> query = em.createQuery(jpql, Orders.class)
                 .setMaxResults(1000);//최대 1000건

         if (ordersSearch.getOrderStatus() != null){
             query = query.setParameter("status", ordersSearch.getOrderStatus());
         }

         if (StringUtils.hasText(ordersSearch.getMemberName())) {
             query = query.setParameter("name", ordersSearch.getMemberName());
         }


         return query.getResultList();
     }



}
