package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.domain.Route;

import java.util.List;

public interface CategoryDao {
    List<Category> finddaohangtiao();

    int findTotalByCid(int cid, String rname);

    List<Route> limitTravelRoute(int currentpage, int rows, int cid, String rname);
}
