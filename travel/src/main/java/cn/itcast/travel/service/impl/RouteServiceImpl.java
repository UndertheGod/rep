package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.service.RouteService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: java类作用描述
 * @Author: zhangjiale
 * @CreateDate: 2019/3/4 17:10
 * @Version: 1.0
 */
public class RouteServiceImpl implements RouteService {
    RouteDao rd= new RouteDaoImpl();



    ObjectMapper om = new ObjectMapper();

    @Override
    public String findOne(String strrid) {
        int rid = 0;
        if(strrid !=null && strrid.length()>0){
            rid = Integer.parseInt(strrid);

        }
        Route r = rd.findOne(rid);
        //根据rid查询到route对象，然后去route对象中获取对应的sid值
        int sid = r.getSid();
        //然后通过sid的值去查询商家对象，然后返回查询到的商家对象
        Seller seller = rd.sellerId(sid);
        r.setSeller(seller);
        //根据rid去查询图片表图片，需要返回一个list集合
        List<RouteImg> list = rd.imgByRid(rid);
        r.setRouteImgList(list);
        String json = null ;
        try {
             json = om.writeValueAsString(r);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    @Override
    public Favorite favorite(int uid,int rid) {
        Favorite favorite = rd.favorite(uid,rid);
        return favorite;
    }

    //收藏写入数据库
    @Override
    public void dofavorite(int rid, int uid) {
        rd.dofavorite(rid,uid);

    }
    //移除收藏
    @Override
    public int unfavorite(int rid, int uid) {

        return  rd.unfavorite(rid,uid);
    }

    @Override
    public void findMyFavorite(int uid) {

    }

    @Override
    public String favoriteCount(int uid) {
      Map<String,Object> map = new HashMap<>();
        int count = rd.favoriteCount(uid);
        map.put("count",count);
        String json = null ;
        try {
           json =  om.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return json;
    }


}
