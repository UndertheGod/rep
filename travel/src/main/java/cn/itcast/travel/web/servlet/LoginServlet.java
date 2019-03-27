package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
/*        //获取验证码
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
            UserService us = new UserServiceImpl();
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
                if(auto.equals("on")) {
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
            response.getWriter().write(json);*/

    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
