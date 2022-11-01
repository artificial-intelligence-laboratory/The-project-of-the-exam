package com.gcc.fns.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gcc.fns.model.entity.Order;
import com.gcc.fns.model.vo.OrderDetailsVo;

/**
 * @author xiaozhi
 * @description 订单服务
 * @create 2022-10-2022/10/30 16:14
 */
public interface OrderService extends IService<Order>{

    OrderDetailsVo queryOrderDetailsByOrderId(Long id, boolean isSelectFromUser);
}
