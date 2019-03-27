package cn.itcast.travel.web.filter;

import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import cn.itcast.travel.util.Cookieutils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter("/*")
public class AutoLoginFilter implements Filter {
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        //首先检查用户是否已经登陆
        HttpServletRequest request = (HttpServletRequest) req;
        //从session中获取用户
        User user = (User) request.getSession().getAttribute("user");
        if(user!=null){
            //user不为空代表用户已经登陆
            //那么直接放行
            chain.doFilter(request, resp);
        }else{
            //如果用户没有登陆，那么检查用户的客户端是否有cookie的存在
            Cookie[] cookies = request.getCookies();
            Cookie autoLogin = Cookieutils.findcookie(cookies, "AutoLogin");
            if(autoLogin==null){
                //如果cookie不存在，那么直接放行
                chain.doFilter(request, resp);
            }else{
                //如果用户的cookie是存在的
                // 那么我们要判断用户cookie中的账号密码是否和数据库中的一致，因为可以通过手机端来修改密码而cookie是存在浏览器
                String[] split = autoLogin.getValue().split("&");
                String username = split[0];
                String password = split[1];
                //拿到分割后的账号密码，通过调用方法，去查询数据库中，该用户是否能查询到
                //如果查询不到那么无法做自动登陆，直接放行
                UserService us = new UserServiceImpl();
                User userAuto = us.findUserByusernameAndpassword(username, password);
                if(userAuto==null){
                    //用户修改了账号和密码，用原来保存在浏览器中的cookie中的账号和密码查询不到用户
                    //放行
                    chain.doFilter(request, resp);
                }else{
                    //如果使用用户的cookie能进行登陆
                    //那么我们就把获取到的对象存进session中，
                    //!!!自动登陆主要为了实现在页头是否能直接显示名字，也就是在session能找到存在的用户对象那么能显示登陆状态

                    //那么我们只要把能根据cookie分解出来的账号和密码查询到用户，那么我们直接把该用户存在session中就能直接显示
                    //首先我们需要在客户登陆的时候传递一个cookie给浏览器进行保存
                    //等浏览器再次打开的时候会有携带着这个自动登陆的cookie过来，那么我们会进行过滤
                    request.getSession().setAttribute("user",userAuto);
                    chain.doFilter(request, resp);

                }

            }

        }


    }

    public void init(FilterConfig config) throws ServletException {

    }

    public void destroy() {
    }


}
