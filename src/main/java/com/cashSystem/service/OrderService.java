package com.cashSystem.service;

import com.cashSystem.dao.BaseDao;
import com.cashSystem.dao.OrderDao;
import com.cashSystem.entity.Order;

import java.util.List;

public class OrderService extends BaseDao {
    private OrderDao orderDao;
    public OrderService(){
        orderDao = new OrderDao();
    }
    public boolean commitOrder(Order order) {
        //提交订单
        return this.orderDao.insertOrder(order);
    }
    public List<Order> queryOrderByAccount(Integer accountId) {
        return this.orderDao.queryOrderByAccount(accountId);
    }


}
