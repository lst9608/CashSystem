package com.cashSystem.cmd.command.impl.goods;

import com.cashSystem.cmd.command.Subject;
import com.cashSystem.cmd.command.annotation.AdminCommand;
import com.cashSystem.cmd.command.annotation.CommandMeta;
import com.cashSystem.cmd.command.annotation.CustomerCommand;
import com.cashSystem.cmd.command.impl.AbstractCommand;
import com.cashSystem.entity.Goods;

import java.util.List;

@CommandMeta(
        name = "LLSP",
        desc = "浏览商品",
        group = "商品信息"
)

@AdminCommand
@CustomerCommand
public class GoodsBrowseCommand extends AbstractCommand {
    @Override
    public void execute(Subject subject) {
        System.out.println("商品浏览");
        List<Goods> goodsList = this.goodsService.queryAllGoods();

        if(goodsList.isEmpty()) {
            System.out.println("没有商品");
        }else {
            for (Goods goods : goodsList) {
                System.out.println(goods);
            }
        }
    }
}
