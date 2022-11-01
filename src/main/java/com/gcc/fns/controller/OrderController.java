package com.gcc.fns.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gcc.fns.common.enums.OrderStatus;
import com.gcc.fns.common.utils.GraceJSONResult;
import com.gcc.fns.common.utils.UserHolder;
import com.gcc.fns.model.dto.OrderInfoDto;
import com.gcc.fns.model.dto.OrderStatusDto;
import com.gcc.fns.model.dto.PageDto;
import com.gcc.fns.model.dto.UpdateOrderStatusDto;
import com.gcc.fns.model.entity.Order;
import com.gcc.fns.model.vo.AppUserInfoVo;
import com.gcc.fns.model.vo.OrderDetailsVo;
import com.gcc.fns.model.vo.OrderInfoVo;
import com.gcc.fns.service.OrderService;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @author xiaozhi
 * @description 订单模块controller
 * @create 2022-10-2022/10/30 16:03
 */
@Api(value = "订单接口", tags = "订单接口")
@RestController
@RequestMapping("/order")
public class OrderController {

    @Resource
    private OrderService orderService;

    @ApiOperation(value = "获取首页订单列表", notes = "获取首页订单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cur", value = "当前页", dataType = "int", required = true, paramType = "query"),
            @ApiImplicitParam(name = "count", value = "单页的个数", dataType = "int", required = true, paramType = "query")
    })
    @GetMapping("/getOrderList")
    public GraceJSONResult getOrderList(@ApiIgnore PageDto pageDto) {
        Page<Order> page = new Page<>(pageDto.getCur(), pageDto.getCount());
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("status", OrderStatus.TO_BE_RECEIVED.getCode());
        wrapper.orderByDesc("create_time");
        Page<Order> pages = orderService.page(page, wrapper);
        List<Order> list = pages.getRecords();
        List<OrderInfoVo> orderInfoVos = BeanUtil.copyToList(list, OrderInfoVo.class);
        return GraceJSONResult.ok(orderInfoVos);
    }

    @ApiOperation(value = "获取订单详情", notes = "获取订单详情" +
            "<br>订单详情有两种状态：" +
            "<br>①当前用户是接单用户，查看订单详情，需要返回发单用户信息，页面显示的是订单详情的页面" +
            "<br>②当前用户是发单用户，查看订单详情，就是查看是谁接了单子，那么显示的就是接单用户信息，页面显示的是已创建订单的页面" +
            "<br>其他的状态就是根据单子的情况进行修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", value = "订单ID", dataType = "long", required = true, paramType = "query"),
            @ApiImplicitParam(name = "isIssue", value = "true为查询发单，false为查询接单", dataType = "boolean", required = true, paramType = "query")
    })
    @GetMapping("/getOrderDetails")
    public GraceJSONResult getOrderDetails(Long orderId, boolean isIssue) {
        OrderDetailsVo orderDetailsVo = orderService.queryOrderDetailsByOrderId(orderId, isIssue);
        return GraceJSONResult.ok(orderDetailsVo);
    }

    @ApiOperation(value = "获取我的订单列表", notes = "我的订单有两种类型：我的接单我的发单，isIssue字段用来判断是查询我的接单还是我的发单" +
            "<br>status字段作用：订单有多个状态，当用户查询发布的单子已被接取时，可以传入状态为待结束的状态码，以此类推......")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cur", value = "当前页", dataType = "int", required = true, paramType = "query"),
            @ApiImplicitParam(name = "count", value = "单页的个数", dataType = "int", required = true, paramType = "query"),
            @ApiImplicitParam(name = "status", value = "订单状态", dataType = "int", required = true, paramType = "query"),
            @ApiImplicitParam(name = "isIssue", value = "查询类型，true为接单，false为发单", dataType = "boolean", required = true, paramType = "query")
    })
    @GetMapping("/getOrderByMe")
    public GraceJSONResult getOrderByFromUser(@ApiIgnore PageDto pageDto,
                                              @ApiIgnore OrderStatusDto orderStatusDto) {
        AppUserInfoVo user = UserHolder.getUser();
        Page<Order> page = new Page<>(pageDto.getCur(), pageDto.getCount());
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        // 根据传来的字段判断是查接单还是发单
        String selectByUserStr = orderStatusDto.getIsIssue() ? "from_user_id": "target_user_id";
        // 因为都是我的，所以直接使用当前用户去查询即可
        wrapper.eq(selectByUserStr, user.getId());
        wrapper.eq("status", orderStatusDto.getStatus());
        // 根据接单时间进行排序
        wrapper.orderByDesc("receiving_time");
        Page<Order> pages = orderService.page(page, wrapper);
        List<Order> list = pages.getRecords();
        List<OrderInfoVo> orderInfoVos = BeanUtil.copyToList(list, OrderInfoVo.class);
        return GraceJSONResult.ok(orderInfoVos);
    }

    @ApiOperation(value = "创建订单", notes = "创建订单，除了注意事项可以为空，其余的都不能为空")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title", value = "标题", dataType = "String", required = true),
            @ApiImplicitParam(name = "details", value = "详情", dataType = "String", required = true),
            @ApiImplicitParam(name = "beCareful", value = "注意事项", dataType = "String", required = false),
            @ApiImplicitParam(name = "workPlace", value = "工作地点", dataType = "String", required = true),
            @ApiImplicitParam(name = "fee", value = "金额", dataType = "float", required = true),
            @ApiImplicitParam(name = "typeId", value = "订单类型", dataType = "int", required = true)
    })
    @PostMapping("/createOrder")
    public GraceJSONResult createOrder(@ApiIgnore @RequestBody @Valid OrderInfoDto orderInfoDto) {
        AppUserInfoVo user = UserHolder.getUser();
        Order order = BeanUtil.toBean(orderInfoDto, Order.class);
        order.setFromUserId(user.getId());
        // 审核状态
        order.setStatus(OrderStatus.TO_BE_REVIEWED.getCode());
        boolean isSuccessful = orderService.save(order);
        if (!isSuccessful) {
            return GraceJSONResult.errorMsg("创建订单失败");
        }
        return GraceJSONResult.ok();
    }

    @ApiOperation(value = "修改订单状态", notes = "通过修改状态码来更新订单的状态" +
            "<br>状态码：0-待审核 1-代接取 2-待结束 3-已结束 4-待验收" +
            "<br>说明：接单和取消接单就是改变订单状态" +
            "<br>①将状态修改为待结束，那么单子就是被人接取的状态" +
            "<br>②将状态修改为待接取，那么单子就是被人取消了或者未被接取，所以取消订单就将状态修改为代接取即可")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", value = "订单ID", dataType = "long", required = true),
            @ApiImplicitParam(name = "status", value = "订单状态", dataType = "int", required = true)
    })
    @PutMapping("/updateStatus")
    public GraceJSONResult updateOrderStatus(@ApiIgnore @RequestBody @Valid UpdateOrderStatusDto updateOrderStatusDto ) {
        Long orderId = updateOrderStatusDto.getOrderId();
        Integer status = updateOrderStatusDto.getStatus();
        AppUserInfoVo user = UserHolder.getUser();
        UpdateWrapper<Order> wrapper = new UpdateWrapper<>();
        if (status == OrderStatus.TO_BE_RECEIVED.getCode()) {
            // 取消接单 -> 将接单用户ID设为空
            wrapper.set("target_user_id", null);
        } else if (status == OrderStatus.TO_BE_COMPLETED.getCode()) {
            // 接单 -> 将当前的用户id赋值
            wrapper.set("target_user_id", user.getId());
        }
        wrapper.eq("id", orderId);
        wrapper.set("status", status);
        boolean isSuccessful = orderService.update(wrapper);
        if (!isSuccessful) {
            return GraceJSONResult.errorMsg("更新状态失败");
        }
        return GraceJSONResult.ok();
    }

    @ApiOperation(value = "删除订单", notes = "删除订单")
    @ApiParam()
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", value = "订单ID",dataType = "long", required = true)
    })
    @DeleteMapping("/deleteOrder")
    public GraceJSONResult createOrder(Long orderId) {
        boolean isSuccessful = orderService.removeById(orderId);
        if (!isSuccessful) {
            return GraceJSONResult.errorMsg("删除订单失败");
        }
        return GraceJSONResult.ok();
    }

}
