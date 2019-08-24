package com.cashSystem.service;

import com.cashSystem.dao.BaseDao;
import com.cashSystem.dao.GoodsDao;
import com.cashSystem.entity.Goods;

import java.util.List;

public class GoodsService extends BaseDao {
  private   GoodsDao goodsDao;
  public GoodsService(){
      goodsDao = new GoodsDao();
  }
  public List<Goods> queryAllGoods(){
      return this.goodsDao.queryAllGoods();
  }
    public boolean putAwayGoods(Goods goods) {
        return this.goodsDao.insertGoods(goods);
    }
    public Goods getGoods(int goodsId) {
        return this.goodsDao.queryGoodsById(goodsId);
    }
    public boolean soldOutGoods(int goodsId) {
        return this.goodsDao.deleteGoods(goodsId);
    }
    public boolean updateGoods(Goods goods) {
        return this.goodsDao.modifyGoods(goods);
    }
    public  boolean updateGoodsAfterPay(Goods goods,int goodsNum) {
        return this.goodsDao.updateGoodsAfterPay(goods,goodsNum);
    }
}
