package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.Page;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.service.impl.CategoryServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/category/*")
public class CategoryServlet extends BaseServlet {
    CategoryService cs = new CategoryServiceImpl();
    //导航条
    public void finddaohangtiao(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            //调用service方法.获取到台的json数据
        String json = cs.finddaohangtiao();
        //拿到json直接回应给前台
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(json);


    }

    //分类查询旅游线路
    public void travelRoute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //从前台获取旅游路线的current,rows,cid
        String strcid = request.getParameter("cid");
        String strcurrentpage = request.getParameter("currentpage");
        String strrows = request.getParameter("rows");
        String rname = request.getParameter("rname");
        rname = new String(rname.getBytes("iso-8859-1"),"utf-8");
        //保证传递到后台数据的安全性，要进行判断
        int cid = 0;
        int currentpage = 0;
        int rows = 0;
        //如果直接在首页搜索关键字进行查询，那么可能从页面查来的cid被赋值为"null",所以要在判断的时候排除这种情况
        if(strcid !=null && strcid.length()>0  && ! "null".equals(strcid)){
                //如果字符串的cid不为空且长度大于0，那么再进行转换成int类型
             cid = Integer.parseInt(strcid);
        }
        if(strcurrentpage!=null && strcurrentpage.length()>0){
            currentpage = Integer.parseInt(strcurrentpage);
        }else{
            currentpage=1;
        }
        //
        if(strrows!=null && strrows.length()>0){
            rows = Integer.parseInt(strrows);
        }else{
            //如果没有传递rows的值
            //那么就给他一个初始的默认值
            rows=8;
        }

            //处理完所有前台传递过来的参数之后，调用service方法将参数传递进去
            String json = cs.travelRoute(currentpage, rows, cid,rname);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(json);






    }


}
