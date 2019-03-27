package cn.itcast.travel.service;


import cn.itcast.travel.domain.Favorite;

public interface RouteService {
    String findOne(String strrid);

    Favorite favorite(int uid,int rid);

    void dofavorite(int rid,int uid);

    int unfavorite(int rid, int uid);

    void findMyFavorite(int uid);

    String favoriteCount(int uid);
}
