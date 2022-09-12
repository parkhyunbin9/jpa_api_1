package com.daou.shop.api;

import com.daou.shop.domain.Order;
import com.daou.shop.domain.OrderSearch;
import com.daou.shop.repository.OrderRepository;
import com.daou.shop.repository.order.simplequery.OrderSimpleQueryDto;
import com.daou.shop.repository.order.simplequery.OrderSimpleQueryRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * XtoOne (ManyToOne, OneToOne)
 * Order
 * Order -> Member
 * Order -> Delivery
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;
    private final OrderSimpleQueryRespository orderSimpleQueryRespository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        for (Order order : all) {
            order.getMember().getName();      // Lazy 강제 초기화
            order.getDelivery().getAddress(); // Lazy 강제 초기화
        }
        return all;
    }

    @GetMapping("/api/v2/simple-orders")
    public List<OrderSimpleQueryDto> ordersV2() {
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());
        return orders.stream()
                .map(OrderSimpleQueryDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/api/v3/simple-orders")
    public List<OrderSimpleQueryDto> ordersV3() {
        List<Order> orders = orderRepository.findAllWithMemberDeilvery();
        return orders.stream()
                .map(OrderSimpleQueryDto::new)
                .collect(Collectors.toList());

    }

    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDto> ordersV4() {
        return orderSimpleQueryRespository.findOrderDtos();
    }
}
