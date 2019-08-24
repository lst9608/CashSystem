package com.cashSystem.dao;

import com.cashSystem.entity.Account;
import com.cashSystem.entity.Goods;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
//浏览商品
public class GoodsDao extends BaseDao{
    public List<Goods> queryAllGoods(){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Goods> goodsList = new ArrayList<>();
        try {
            connection = this.getConnection(true);
            String sql = "select id,name,introduce,stock,unit,price,discount from goods";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
               goodsList.add(this.extractGoods(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeResource(resultSet, preparedStatement, connection);
        }
        return goodsList;
    }
    private Goods extractGoods(ResultSet resultSet) throws SQLException {
        Goods goods = new Goods();
        goods.setId(resultSet.getInt("id"));
        goods.setName(resultSet.getString("name"));
        goods.setIntroduce(resultSet.getString("introduce"));
        goods.setStock(resultSet.getInt("stock"));
        goods.setUnit(resultSet.getString("unit"));
        goods.setPrice(resultSet.getInt("price"));
        goods.setDiscount(resultSet.getInt("discount"));
        return goods;
    }

    //上架商品
    public boolean insertGoods(Goods goods) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean effect = false;
        try {
            String sql = "insert into goods (name,introduce, stock, unit, price, discount) value (?,?,?,?,?,?)";
            connection = this.getConnection(true);
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, goods.getName());
            preparedStatement.setString(2, goods.getIntroduce());
            preparedStatement.setInt(3, goods.getStock());
            preparedStatement.setString(4, goods.getUnit());
            preparedStatement.setInt(5, goods.getPrice());
            preparedStatement.setInt(6, goods.getDiscount());
            effect = preparedStatement.executeUpdate() == 1;
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                goods.setId(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeResource(resultSet, preparedStatement, connection);
        }
        return effect;
    }
    //通过id来查找商品
    public Goods queryGoodsById(int goodsId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.getConnection(true);
            String sql = "select id, name, introduce, stock, unit, price, discount from goods where  id =?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, goodsId);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return extractGoods(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeResource(resultSet, statement, connection);
        }
        return null;
    }

    //下架商品
    public boolean deleteGoods(int goodsId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.getConnection(true);
            String sql = "delete  from goods where  id=?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, goodsId);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeResource(resultSet, statement, connection);
        }
        return false;
    }
    public boolean modifyGoods(Goods goods) {
        //名称，简介，价格，折扣，库存
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.getConnection(true);
            String sql = "update goods set name=? , introduce =?,  stock=? , price=?, discount =? where  id= ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, goods.getName());
            statement.setString(2, goods.getIntroduce());
            statement.setInt(3, goods.getStock());
            statement.setInt(4, goods.getPrice());
            statement.setInt(5, goods.getDiscount());
            statement.setInt(6, goods.getId());
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeResource(resultSet, statement, connection);
        }
        return false;
    }

    //支付之后  更新货物库存
    public boolean updateGoodsAfterPay(Goods goods,int goodsNum) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.getConnection(true);
            String sql = "update goods set stock=? where  id= ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, goods.getStock() - goodsNum);
            statement.setInt(2, goods.getId());
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeResource(resultSet, statement, connection);
        }
        return false;

    }

}
