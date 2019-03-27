package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/registUserServlet")
public class RegistUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
/*            //获取前台数据
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
        UserService us = new UserServiceImpl();
        Boolean flag = us.registUser(user);
        //将返回的后台返回的结果封装成一个ResultInfo对象
        ResultInfo ri = new ResultInfo();
        if(flag){
           //注册成功
            System.out.println("<a href='http://localhost:/travel/activeUserServlet?code="+user.getCode()+"'>点击激活,【黑马旅游网】</a>");
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
        }*/
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
