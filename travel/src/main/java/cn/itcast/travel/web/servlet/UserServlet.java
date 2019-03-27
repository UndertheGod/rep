package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import cn.itcast.travel.util.Cookieutils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Random;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    UserService us = new UserServiceImpl();
    public void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取前台数据
        request.setCharacterEncoding("utf-8");
        //验证码的验证
        String check = request.getParameter("check");
        //获取session域中的验证码
        String checkcode_server = (String) request.getSession().getAttribute("CHECKCODE_SERVER");
        request.getSession().removeAttribute("CHECKCODE_SERVER");
        if(checkcode_server !=null && check.equalsIgnoreCase(checkcode_server)){
            Map<String, String[]> map = request.getParameterMap();
            //封装User对象
            //创建User对象
            User user = new User();
            try {
                BeanUtils.populate(user,map);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            //调用Service

            Boolean flag = us.registUser(user);
            //将返回的后台返回的结果封装成一个ResultInfo对象
            ResultInfo ri = new ResultInfo();
            if(flag){
                //注册成功
                System.out.println("<a href='http://localhost:/travel/user/active?code="+user.getCode()+"'>点击激活,【黑马旅游网】</a>");
                ri.setFlag(true);

            }else{
                //注册失败
                ri.setFlag(false);
                ri.setErrorMsg("注册失败");
            }
            //将jsonResultInfo对象序列化位json
            ObjectMapper om = new ObjectMapper();
            String json = om.writeValueAsString(ri);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(json);
        }else{
            ObjectMapper om = new ObjectMapper();
            ResultInfo ri = new ResultInfo();
            ri.setFlag(false);
            ri.setErrorMsg("验证码错误");
            String json = om.writeValueAsString(ri);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(json);
        }
    }

    public void find(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取session域中的User对象
        User user = (User) request.getSession().getAttribute("user");
        //h获取对象后通过变成json方法传给让前台
        ObjectMapper om = new ObjectMapper();
        String json = om.writeValueAsString(user);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(json);
    }


    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取验证码
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        String checkcode = request.getParameter("check");
        String auto_login = request.getParameter("auto_login");
        String checkcode_server = (String) request.getSession().getAttribute("CHECKCODE_SERVER");
        request.getSession().removeAttribute("CHECKCODE_SERVER");
        ResultInfo ri = new ResultInfo();
        if(checkcode_server==null || !checkcode_server.equalsIgnoreCase(checkcode)){
            //验证码错误

            ri.setFlag(false);
            ri.setErrorMsg("验证码错误");

        }else {
            //获取用户所有的提交信息
            Map<String, String[]> mapuser = request.getParameterMap();
            User user = new User();
            try {
                BeanUtils.populate(user,mapuser);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            //把对象传给UserService
          //  UserService us = new UserServiceImpl();
            User userhave = us.findUserByusernameAndpassword(user.getUsername(),user.getPassword());

            if(userhave==null){
                //如果用户位空
                ri.setFlag(false);
                ri.setErrorMsg("用户名或密码错误");
            }
            if(userhave !=null && !"Y".equalsIgnoreCase(userhave.getStatus())){
                //如果用户名不是空，但是没有激活
                ri.setFlag(false);
                ri.setErrorMsg("你尚未激活，请激活！");
            }
            if(userhave!=null && "Y".equalsIgnoreCase(userhave.getStatus())){
                //用户名存在，也激活了
                //那么登陆成功

                request.getSession().setAttribute("user",userhave);
                //自动登陆就是能在页头显示用户的名字，那么只要session中该用户的信息，就能被通过ajax请求进行显示
                //自动登陆主要还是看session中有没有用户的信息存在
                //如果需要进行自动登陆，当用户登陆第一次的时候，需要把用户的账号和密码进行加密通过cookie的形式传递给客户的浏览器
                //当用户再次打开浏览器的时候，发送请求的时候是携带着自动登陆的cookie的，只要
                //自动登陆的cookie中的解析出来账号和密码和数据库一样，那么我们就把该用户保存到session中
                //只要用户信息被保存进session中，那么就能在页头显示该用户的名字
                //假如需要自动登陆，那么必须把用户的账号密码进行存放到cookie中

                //获取自动登陆复选按钮的name
                String auto = request.getParameter("auto_login");
                if("on".equals(auto)) {
                    String username = userhave.getUsername();
                    String password = userhave.getPassword();
                    String automsg = username + "&" + password;
                    //创建cookie对象
                    Cookie ck = new Cookie("AutoLogin", automsg);
                    ck.setMaxAge(60 * 60 * 24 * 7);
                    ck.setPath("/travel");
                    response.addCookie(ck);
                }
                ri.setFlag(true);
            }
        }


        ObjectMapper om = new ObjectMapper();
        String json = om.writeValueAsString(ri);
        response.getWriter().write(json);
    }
    public void checkUsername(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String username = request.getParameter("username");

        //UserService us = new UserServiceImpl();
        String json = us.checkUsername(username);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(json);
    }

    public void destory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //注销
        //销毁session中的对象和cookie(自动登陆)
        request.getSession().invalidate();
        Cookie[] cookies = request.getCookies();
        Cookie autoLogin = Cookieutils.findcookie(cookies, "AutoLogin");
        if(autoLogin!=null){
            autoLogin.setValue("");
            autoLogin.setMaxAge(0);
            autoLogin.setPath("/travel");
            //把cookie设置空，再返回给浏览器
            response.addCookie(autoLogin);
        }

        response.sendRedirect(request.getContextPath()+"/login.html");
    }


    public void activeCode(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取激活码
        String code = request.getParameter("code");
        //调用方法去查询该激活码是否存在此人，防止可能是直接通过地址输入的验证码

        if(code!=null){
           // UserService us = new UserServiceImpl();
            Boolean active = us.active(code);
            String message;
            if(active){
                //如果是真，代表有此人并已经激活验证码
                message = "激活成功，请<a href='login.html'>登陆</a>";
            }else{
                message="激活失败，请联系管理员";
            }
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(message);
        }

    }

}
