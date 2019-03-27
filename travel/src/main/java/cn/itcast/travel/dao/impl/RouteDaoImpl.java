package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;
import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: zhangjiale
 * @CreateDate: 2019/3/4 17:10
 * @Version: 1.0
 */
public class RouteDaoImpl implements RouteDao {
    JdbcTemplate jt = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public Route findOne(int rid) {
        Route route = null;
        try {
            String sql = "select * from tab_route where rid = ? ";
            route = jt.queryForObject(sql, new BeanPropertyRowMapper<Route>(Route.class), rid);

            return route;
        } catch (DataAccessException e) {
            //e.printStackTrace();
        }
        return route;
    }
    //根据rid返回一个商家对象
    @Override
    public Seller sellerId(int sid) {
        Seller seller=null;
        try {
            String sql = "select * from tab_seller where sid = ? ";
            seller = jt.queryForObject(sql, new BeanPropertyRowMapper<Seller>(Seller.class), sid);
            return seller;
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return seller;
    }
    //根据rid返回一个装着图片对象的集合
    @Override
    public List<RouteImg> imgByRid(int rid) {
        String sql = "select * from tab_route_img where rid = ? ";
        List<RouteImg> list = jt.query(sql, new BeanPropertyRowMapper<RouteImg>(RouteImg.class), rid);
        return list;
    }

    @Override
    public Favorite favorite(int uid, int rid) {
        Favorite favorite=null;
        try {
            String sql = "select * from tab_favorite where uid = ? and rid = ?";
            favorite = jt.queryForObject(sql, new BeanPropertyRowMapper<Favorite>(Favorite.class), uid,rid);
            return favorite;
        } catch (DataAccessException e) {
            //e.printStackTrace();
        }
        return  favorite;
    }

    //把收藏写入数据库
    @Override
    public void dofavorite(int rid, int uid) {
        String sql = "insert into tab_favorite values(?,?,?)";
         jt.update(sql, rid,new Date(), uid);
    }
    //取消收藏
    @Override
    public int unfavorite(int rid, int uid) {
        String sql = "delete from tab_favorite where rid = ? and uid = ? ";
        int count = jt.update(sql, rid, uid);
        return count;
    }

    @Override
    public int favoriteCount(int uid) {
        String sql = "select count(*) from tab_favorite where uid = ?";
        Integer count = jt.queryForObject(sql, Integer.class, uid);
        return count;
    }

    //统计个人的收藏次数

}
