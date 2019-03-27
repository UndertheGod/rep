package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.domain.Page;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: zhangjiale
 * @CreateDate: 2019/3/2 21:28
 * @Version: 1.0
 */
public class CategoryServiceImpl implements CategoryService {
    CategoryDao cd = new CategoryDaoImpl();
    Jedis jedis = JedisUtil.getJedis();
    ObjectMapper om = new ObjectMapper();

    //分类查询旅游路线
    @Override
    public String travelRoute(int currentpage, int rows, int cid, String rname) {
        String json = null;
        Page<Route> pg = new Page<>();
        pg.setCurrentpage(currentpage);//设置当前页
        pg.setRows(rows);//设置每页显示的条数
        //根据cid查询总记录数totalcount
        int totalcount = cd.findTotalByCid(cid,rname);
        pg.setTotalcount(totalcount);
        //设置总页数= 总记录数/每页显示的条数
        int totalpage = (totalcount % 2==0 ?     totalcount/rows : totalcount/rows+1 );
        pg.setTotalpage(totalpage);
        //根据 currentpage，rows，cid查询分页要显示的内容装进List集合
        List<Route> routes = cd.limitTravelRoute(currentpage, rows, cid,rname);
        pg.setList(routes);
        int beginpage = 0;
        int endpage = 0;
        //分页条上显示10
        if(totalpage<10){
            beginpage=1;
            endpage=totalpage;
        }else{
            beginpage=currentpage-5;
            endpage=currentpage+4;
            //既然总页数大于10页
            //那么我们要控制上限beginpage=currentpage-5;不能让beginpage<1
            //当beginpage<1条件成立的时候，我们要重新设置endpage和beginpage的值
            if(beginpage<1){
                beginpage=1;
                endpage=beginpage+9;
            }
            //那么我们要控制下限endpage>totalpage;不能让endpage>totalpage
            //当endpage>totalpage条件成立时候,我们要重新进行设置endpage和beginpage的值
            if(endpage>totalpage){
                endpage=totalpage;
                beginpage=endpage-9;
            }
        }
        pg.setBeginpage(beginpage);
        pg.setEndpage(endpage);
        try {
             json = om.writeValueAsString(pg);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return json;
    }


    //导航条查询，需要缓存
    @Override
    public String finddaohangtiao() {
        //首先在缓存中查询是否存在该
        String category_json = jedis.get("Category");
        if(category_json==null || "".equals(category_json) ){
            //System.out.println("去查询数据库");
            //缓存没有去数据库查询
            List<Category> list = cd.finddaohangtiao();
            try {
                category_json = om.writeValueAsString(list);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            jedis.set("Category",category_json);
            jedis.close();
        }else{
            //System.out.println("使用缓存查询");
        }

        return category_json;
    }
}
