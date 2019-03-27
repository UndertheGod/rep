package cn.itcast.travel.web.servlet;

import cn.itcast.travel.util.Cookieutils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/destoryServlet")
public class DestoryServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 /*           //注销
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

        response.sendRedirect(request.getContextPath()+"/login.html");*/



    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
