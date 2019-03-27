package cn.itcast.travel.util;

import javax.servlet.http.Cookie;

/**
 * @Description: java类作用描述
 * @Author: zhangjiale
 * @CreateDate: 2019/3/2 12:40
 * @Version: 1.0
 */
public class Cookieutils {

    public static Cookie findcookie(Cookie[] cookies, String name){
        if(cookies==null){
            //如果位空直接返回Null
            return null;
        }
        for (Cookie cookie : cookies) {
            if(name.equalsIgnoreCase(cookie.getName())){
                    return cookie;
            }
        }

        return null;
    }
}
