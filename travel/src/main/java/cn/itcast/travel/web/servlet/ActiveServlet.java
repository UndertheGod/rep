package cn.itcast.travel.web.servlet;

import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/activeUserServlet")
public class ActiveServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    /*        //获取激活码
        String code = request.getParameter("code");
        //调用方法去查询该激活码是否存在此人，防止可能是直接通过地址输入的验证码

        if(code!=null){
            UserService us = new UserServiceImpl();
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

*/

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
