package com.gcc.fns.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gcc.fns.model.entity.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author xiaozhi
 * @description
 * @create 2022-10-2022/10/28 0:25
 */
@SpringBootTest
public class OrderMapperTest {

    @Autowired
    private OrderMapper orderMapper;

    @Test
    public void testSelectOrderByUserId(){
        Page<Order> page = new Page<>(1, 5);
        List<Order> orders = orderMapper.selectOrderByUserId(page, 11L, true);
        orders.forEach(o -> {
            System.out.println(o.getCreateTime());
        });
    }

    @Test
    public void testInsertOrder(){
        Order order = new Order();
        order.setFromUserId(11L);
        order.setTitle("帮拿快递");
        order.setDetails("帮拿快递");
        order.setBeCareful("易碎物，轻放");
        order.setFee(5.0);
        order.setTypeId(1);
        order.setWorkPlace("26栋菜鸟");
        orderMapper.insert(order);
        System.out.println(order);
    }
}
