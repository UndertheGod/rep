package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: java类作用描述
 * @Author: zhangjiale
 * @CreateDate: 2019/3/2 21:28
 * @Version: 1.0
 */
public class CategoryDaoImpl implements CategoryDao {
    JdbcTemplate jt = new JdbcTemplate(JDBCUtils.getDataSource());
    //对数据库导航条的查询，并进行排序
    @Override
    public List<Category> finddaohangtiao() {
        String sql = "select * from tab_category order by cid";
        List<Category> list = jt.query(sql, new BeanPropertyRowMapper<Category>(Category.class));

        return  list;
    }


    //根据cid查询出该线路的总数

    //cid不存在是由于直接在主页进行搜索
    //如果ranme不存在，cid存在是通过rout_list页面直接点击的搜索，没有输入线路内容的搜索
    //如果rname 和cid都存在那么是通过rout_list页面输入了需要搜索的内容，并进行了搜索
    @Override
    public int findTotalByCid(int cid, String rname) {
        //String sql = "select count(*) from tab_route where cid  = ? ";
        String sql = "select count(*) from tab_route where 1 = 1";
        ArrayList<Object> list = new ArrayList<>();
        StringBuilder sb = new StringBuilder(sql);
        if(cid!=0){
            //如果存在cid
            sb.append(" and cid = ? ");
            list.add(cid);
        }
        if(rname!=null && !"null".equals(rname)){
            //如果rname是存在的
            sb.append(" and rname like ? ");
            list.add("%"+rname+"%");
        }
        Integer count = jt.queryForObject(sb.toString(), Integer.class,list.toArray());

        return count;
    }

    //数据展示分页
    @Override
    public List<Route> limitTravelRoute(int currentpage, int rows, int cid, String rname) {
        int index = (currentpage -1)*rows;//从哪个记录开始查询
       // String sql = "select * from tab_route  where cid  = ? limit ? , ? ";
        String sql = "select * from tab_route where 1 = 1";
        StringBuilder sb = new StringBuilder(sql);
        ArrayList<Object> list = new ArrayList<>();
        if(cid!=0){
            //如果存在cid
            sb.append(" and cid = ? ");
            list.add(cid);
        }
        if(rname!=null && !"null".equals(rname)){
            //如果rname是存在的
            sb.append(" and rname like ? ");
            list.add("%"+rname+"%");
        }

        sb.append("  limit ? , ? ");
        list.add(index);
        list.add(rows);


        return jt.query(sb.toString(), new BeanPropertyRowMapper<Route>(Route.class),list.toArray());
    }
}
