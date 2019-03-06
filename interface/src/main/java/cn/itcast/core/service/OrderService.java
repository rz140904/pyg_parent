package cn.itcast.core.service;

import cn.itcast.core.pojo.entity.Result;
import cn.itcast.core.pojo.order.Order;

public interface OrderService {

    /**
     * 保存订单 涉及到三张表
     * @param order
     */
    public void add(Order order);

    /**
     * 根据支付单号修改,支付状态为已支付
     * @param out_trade_no 支付单号
     */
    public void updatePayLogAndOrderStatus(String out_trade_no);
}
