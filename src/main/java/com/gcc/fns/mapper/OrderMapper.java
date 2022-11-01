package com.gcc.fns.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gcc.fns.model.entity.Order;
import com.gcc.fns.model.vo.OrderDetailsVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author xiaozhi
 * @description 订单mapper
 * @create 2022-10-2022/10/26 22:34
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

    /**|
     * 通过用户ID查询订单，用户有两种行为，查接单人和发单人，所以可以通过判断来将查询的数据减少
     * @param id               订单id
     * @param isSelectFromUser 是否查询的是接单用户，true为接单用户，false为发单用户
     * @return
     */
    OrderDetailsVo selectOrderByOrderId(Long id, boolean isSelectFromUser);


}
