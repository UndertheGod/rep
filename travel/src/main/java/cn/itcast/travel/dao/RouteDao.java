package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;

import java.util.List;

public interface RouteDao {
    Route findOne(int rid);

    Seller sellerId(int id);

    List<RouteImg> imgByRid(int rid);

    Favorite favorite(int uid, int rid);

    void dofavorite(int rid, int uid);

    int unfavorite(int rid, int uid);

    int favoriteCount(int uid);
}
