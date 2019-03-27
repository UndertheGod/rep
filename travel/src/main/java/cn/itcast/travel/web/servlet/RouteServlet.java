package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.RouteServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {
    RouteService rs = new RouteServiceImpl();
    ResultInfo ri = new ResultInfo();
    ObjectMapper om = new ObjectMapper();

    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        //获取rid
        String rid = request.getParameter("rid");
        //通过service层的多表连查，最后返回一个route对象的json串形式
        String json = rs.findOne(rid);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(json);


    }

    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int rid = 0;
        String strrid = request.getParameter("rid");
        if(strrid!=null && strrid.length()>0 && !"null".equals(strrid)){
           rid= Integer.parseInt(strrid);
        }
        //首先判断用户是否已经登陆
        User u = (User) request.getSession().getAttribute("user");
        if (u == null) {
            //如果没有登陆
            ri.setFlag(false);

        } else {
            //如果已经登陆那么
            Favorite favorite = rs.favorite(u.getUid(),rid);
            if (favorite == null) {
                //登陆了未收藏
                ri.setFlag(true);
                ri.setData(2000);
            } else {
                //登陆了已经收藏
                ri.setFlag(true);
                ri.setData(3000);
            }


        }
        String json = om.writeValueAsString(ri);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(json);


    }

    public void favorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String strrid = request.getParameter("rid");
        User user = (User) request.getSession().getAttribute("user");
        int uid = user.getUid();
        int rid = 0;
        if (strrid != null) {
            rid = Integer.parseInt(strrid);
        }

        rs.dofavorite(rid, uid);
        ri.setFlag(true);
        String json = om.writeValueAsString(ri);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(json);
    }


    public void unfavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String strrid = request.getParameter("rid");
        User user = (User) request.getSession().getAttribute("user");
        int uid = user.getUid();
        int rid = 0;
        if (strrid != null) {
            rid = Integer.parseInt(strrid);
        }
        int count = rs.unfavorite(rid, uid);
        if(count>0){
            ri.setFlag(true);
            String json = om.writeValueAsString(ri);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(json);

        }



    }

    public void myfavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        int uid = user.getUid();
        //根据用户的uid查询tab-favorite表，得到rid
        rs.findMyFavorite(uid);

            //然后根据rid去查询tab_route表




    }



    public void favoriteCount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        int uid = user.getUid();

        String json = rs.favoriteCount(uid);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(json);


    }


}
