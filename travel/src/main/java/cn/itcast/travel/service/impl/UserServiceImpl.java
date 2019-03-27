package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.UuidUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: java类作用描述
 * @Author: zhangjiale
 * @CreateDate: 2019/2/28 17:07
 * @Version: 1.0
 */
public class UserServiceImpl implements UserService {
    private UserDao ud = new UserDaoImpl();

    //注册用户
    @Override
    public Boolean registUser(User user) {
//        Map<String,Object> map = new HashMap<String,Object>();
//        ObjectMapper om = new ObjectMapper();
//        String json=null;
        String username = user.getUsername();
        if(username.equalsIgnoreCase("heima") || username.equals("") || username==null){
            return false;
        }
        boolean matches = username.matches("[a-zA-Z][a-zA-Z0-9_]{4,15}");
        if (matches) { //如果传入的用户名符合正则表达式
            //进行数据库查询是否有该用户
            if (ud.findUser(user)) {
                //如果数据库查询到该用户
                //那么就false
                //map.put("regist",false);
                return false;
            } else {
                boolean matchesp = user.getPassword().matches("\\w{5,17}");
                boolean matchesn = user.getName().matches("^\\w{6,12}$");
                boolean equalsb = user.getBirthday().matches("^\\d{4}-\\d{1,2}-\\d{1,2}$");
                boolean matchest = user.getTelephone().matches("^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$");
                boolean matchese = user.getEmail().matches("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");

                //数据库查询没有查询到该用户
                if (matchesp && matchesn && equalsb && matchest && matchese) {
                    //如果剩下的表单数据符合正则，那么就进行写入保存用户数据
                    user.setCode(UuidUtil.getUuid());
                    user.setStatus("N");
                    int count = ud.save(user);
                    if(count==1){
                        //储存成功，就发送邮件


                        return true;



                    }else{
                        //储存不成功
                        return false;
                    }

                } else {
                    //如果不正确，那么就直接返回false
//                    map.put("regist",false);
                    return false;
                }
            }
        } else {
            //如果不符合正则,那么直接返回fasle
//            map.put("regist",false);
            return false;
        }

    }


    //异步检测用户名是否存在
    @Override
    public String checkUsername(String username) {
        int count = ud.checkUsername(username);
        String json = null;
        Map<String, Object> map = new HashMap<>();
        if (count == 1) {
            map.put("userExit", true);
            map.put("message", "该用户名不可用");

        } else {
            map.put("userExit", false);
            map.put("message", "该用户名可用");
        }
        ObjectMapper om = new ObjectMapper();
        try {
            json = om.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    @Override
    public Boolean active(String code) {
        //根据激活查询数据库是否有此人、
        User userByCode = ud.findUserByCode(code);
        if(userByCode != null){
            //如果该对象不是空，那么把他的激活码状态进行改变
            //传入user对象
            ud.changeStatus(userByCode);
            userByCode.setCode(null);
            return  true;
        }


        return false;
    }

    @Override
    public User findUserByusernameAndpassword(String username,String password) {

        return ud.findUserByusernameAndpassword(username,password);
    }

}
