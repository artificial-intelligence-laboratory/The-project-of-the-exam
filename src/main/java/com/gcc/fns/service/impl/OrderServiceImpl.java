package com.gcc.fns.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gcc.fns.mapper.OrderMapper;
import com.gcc.fns.model.entity.Order;
import com.gcc.fns.model.vo.OrderDetailsVo;
import com.gcc.fns.service.OrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author xiaozhi
 * @description
 * @create 2022-10-2022/10/30 16:14
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Override
    public OrderDetailsVo queryOrderDetailsByOrderId(Long id, boolean isSelectFromUser) {
        return orderMapper.selectOrderByOrderId(id, isSelectFromUser);
    }
}
