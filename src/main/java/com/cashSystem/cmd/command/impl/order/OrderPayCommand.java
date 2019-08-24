package com.cashSystem.cmd.command.impl.order;

import com.cashSystem.Common.OrderStatus;
import com.cashSystem.cmd.command.Subject;
import com.cashSystem.cmd.command.annotation.CommandMeta;
import com.cashSystem.cmd.command.annotation.CustomerCommand;
import com.cashSystem.cmd.command.impl.AbstractCommand;
import com.cashSystem.entity.Goods;
import com.cashSystem.entity.Order;
import com.cashSystem.entity.OrderItem;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@CommandMeta(
        name = "ZFDD",
        desc = "支付订单",
        group = "我的订单"
)
@CustomerCommand
public class OrderPayCommand extends AbstractCommand {
    @Override
    public void execute(Subject subject) {
        System.out.println("请输入你要购买的货物id以" +
                "及数量多个货物之间使用,号隔开：格式：1-8,3-5");
        String string = scanner.nextLine();
        String[] strings = string.split(",");

        List<Goods> goodsList =  new ArrayList<>();

        for (String stringtmp : strings ) {//1-8
            String[] str = stringtmp.split("-");
            Goods goods = this.goodsService.getGoods(Integer.parseInt(str[0]));
            goods.setBuyGoodsNum(Integer.parseInt(str[1]));
            goodsList.add(goods);
        }

        Order order = new Order();
        order.setId(String.valueOf(System.currentTimeMillis()));
        order.setAccount_id(subject.getAccount().getId());
        order.setAccount_name(subject.getAccount().getName());
        order.setCreate_time(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.PAYING);
        int totalMoney = 0;
        int actualAmount = 0;
        //计算需要的总金额
        for (Goods goods : goodsList) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(order.getId());
            orderItem.setGoodsId( goods.getId());
            orderItem.setGoodsName(goods.getName());
            orderItem.setGoodsUnit(goods.getUnit());
            orderItem.setGoodsPrice(goods.getPrice());
            orderItem.setGoodsDiscount(goods.getDiscount());
            orderItem.setGoodsNum(goods.getBuyGoodsNum());
            orderItem.setGoodsIntroduce(goods.getIntroduce());

            order.getOrderItems().add(orderItem);

            int currentMoney = goods.getPrice()*goods.getBuyGoodsNum();
            totalMoney += currentMoney;
            actualAmount += currentMoney*goods.getDiscount() / 100;
        }

        order.setActual_amount(actualAmount);
        order.setTotal_money(totalMoney);

        /*
         * 加入订单项当中
         * */
        System.out.println(order);
        System.out.println("以上为订单信息，请支付：zf");
        String confirm = scanner.next();
        if("zf".equalsIgnoreCase(confirm)) {
            order.setFinish_time(LocalDateTime.now());
            order.setOrderStatus(OrderStatus.OK);

            boolean effect = this.orderService.commitOrder(order);

            if(effect) { //说明插入订单和订单项成功
                //将goods表中的 具体货物数量更新
                for (Goods goods : goodsList) {
                    boolean isUpdate = this.goodsService.updateGoodsAfterPay(goods,goods.getBuyGoodsNum());
                    if(isUpdate) {
                        System.out.println("库存已经更新");
                    }
                }
            }
        }else {
            System.out.println("您未成功支付！");
        }
    }
}
