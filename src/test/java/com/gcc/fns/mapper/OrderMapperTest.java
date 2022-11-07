package com.gcc.fns.mapper;

import com.gcc.fns.model.entity.Order;
import com.gcc.fns.model.vo.OrderDetailsVo;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

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
    public void selectOrderByOrderId(){
        OrderDetailsVo orderDetailsVo = orderMapper.selectOrderByOrderId(1586669634512232450L, false);
        System.out.println(orderDetailsVo);
    }

    @Test
    public void testInsertOrder(){
        for (int i = 0; i < 20; i++) {
            Order order = new Order();
            order.setFromUserId(22L);
            order.setTitle("帮拿快递");
            order.setDetails("帮拿快递");
            order.setBeCareful("易碎物，轻放");
            order.setFee(5.0);
            order.setTypeId(1);
            order.setWorkPlace("26栋菜鸟");
            order.setStatus(1);
            order.setStartTime(new Date());
            order.setEndTime(DateUtils.addDays(new Date(), 1));
            orderMapper.insert(order);
        }
    }

}
