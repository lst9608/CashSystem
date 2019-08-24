package com.cashSystem.dao;

import com.cashSystem.Common.OrderStatus;
import com.cashSystem.entity.Order;
import com.cashSystem.entity.OrderItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDao extends BaseDao{
    public boolean insertOrder(Order order) {
        Connection connection = null;
        PreparedStatement statement = null;
        String insertOrderSql = "insert into `order`(id, account_id, create_time, finish_time, actual_amount, total_money, order_status, account_name) values (?,?,?,?,?,?,?,?)";
        String insertOrderItemSql = "insert into order_item(order_id, goods_id, goods_name, goods_introduce, goods_num, goods_unit, goods_price, goods_discount) VALUES (?,?,?,?,?,?,?,?)";
        boolean effect = false;
        try {
            connection = this.getConnection(false);
            statement = connection.prepareStatement(insertOrderSql);
            statement.setString(1, order.getId());
            statement.setInt(2, order.getAccount_id());
            statement.setTimestamp(3, Timestamp.valueOf(order.getCreate_time()));
            statement.setTimestamp(4, order.getFinish_time() == null ? null : Timestamp.valueOf(order.getCreate_time()));
            statement.setInt(5, order.getActual_amount());
            statement.setInt(6, order.getTotal_money());
            statement.setInt(7, order.getOrderStatus().getFlg());
            statement.setString(8, order.getAccount_name());
            effect = statement.executeUpdate() == 1;
            statement = connection.prepareStatement(insertOrderItemSql);
            for (OrderItem orderItem : order.getOrderItems()) {
                statement.setString(1, orderItem.getOrderId());
                statement.setInt(2, orderItem.getGoodsId());
                statement.setString(3, orderItem.getGoodsName());
                statement.setString(4, orderItem.getGoodsIntroduce());
                statement.setInt(5, orderItem.getGoodsNum());
                statement.setString(6, orderItem.getGoodsUnit());
                statement.setInt(7, orderItem.getGoodsPrice());
                statement.setInt(8, orderItem.getGoodsDiscount());
                //批量执行sql语句
                statement.addBatch();
            }
            //提交一批要执行的更新命令
            int[] effects = statement.executeBatch();
            for (int i : effects) {
                effect = i == 1;
            }
            if (effect) {
                connection.commit();
            } else {
                try {
                    //回滚操作
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            this.closeResource(null, statement, connection);
        }

        return effect;
    }

    private void extractOrder(final Order order, ResultSet resultSet) throws SQLException {
        order.setId(resultSet.getString("order_id"));
        order.setAccount_id(resultSet.getInt("account_id"));
        order.setCreate_time(resultSet.getTimestamp("create_time").toLocalDateTime());
        Timestamp finishTime = resultSet.getTimestamp("finish_time");
        if (finishTime != null) {
            order.setFinish_time(finishTime.toLocalDateTime());
        }
        order.setActual_amount(resultSet.getInt("actual_amount"));
        order.setTotal_money(resultSet.getInt("total_money"));
        order.setOrderStatus(OrderStatus.valueOf(resultSet.getInt("order_status")));
        order.setAccount_name(resultSet.getString("account_name"));
    }

    private OrderItem extractOrderItem(ResultSet resultSet) throws SQLException {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(resultSet.getInt("item_id"));
        orderItem.setGoodsId(resultSet.getInt("goods_id"));
        orderItem.setGoodsName(resultSet.getString("goods_name"));
        orderItem.setGoodsIntroduce(resultSet.getString("goods_introduce"));
        orderItem.setGoodsNum(resultSet.getInt("goods_num"));
        orderItem.setGoodsUnit(resultSet.getString("goods_unit"));
        orderItem.setGoodsPrice(resultSet.getInt("goods_price"));
        orderItem.setGoodsDiscount(resultSet.getInt("goods_discount"));
        return orderItem;
    }
    //  订单浏览 预留
    public List<Order> queryOrderByAccount(Integer accountId) {
        List<Order> orderList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.getConnection(true);
            String sql = this.getSql("@query_order_by_account");
            statement = connection.prepareStatement(sql);

            statement.setInt(1, accountId);
            //根据accountId 进行查询数据库结果 到resultSet 集合当中
            resultSet = statement.executeQuery();

            Order order = null;
            while (resultSet.next()) {
                if (order == null) {
                    order = new Order();
                    this.extractOrder(order, resultSet);
                    orderList.add(order);
                }

                String orderId = resultSet.getString("order_id");
                //不相同重新生成一个订单对象 添加到订单的List
                if (!orderId.equals(order.getId())) {
                    order = new Order();
                    this.extractOrder(order, resultSet);
                    orderList.add(order);
                }
                //往当前订单内添加 具体的订单项 orderItem
                OrderItem orderItem = this.extractOrderItem(resultSet);
                order.getOrderItems().add(orderItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            this.closeResource(resultSet, statement, connection);
        }
        return orderList;
    }

}
