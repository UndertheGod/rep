package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: zhangjiale
 * @CreateDate: 2019/2/28 17:16
 * @Version: 1.0
 */
public class UserDaoImpl implements UserDao {
    private JdbcTemplate jt = new JdbcTemplate(JDBCUtils.getDataSource());

    //检测用户是否存在
    @Override
    public Boolean findUser(User user) {
        try {
            String sql = "select * from tab_user where username = ?";
            User registusername = jt.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), user.getUsername());
            return true;
        } catch (DataAccessException e) {
            return false;
        }

    }

    @Override
    public int checkUsername(String username) {
        String sql = "select count(*) from tab_user where username = ? ";
        Integer count = jt.queryForObject(sql, Integer.class, username);
        return count;
    }
    //添加用户
    @Override
    public int save(User user) {
        String sql = "insert into tab_user value(null,?,?,?,?,?,?,?,?,?)";
        int count = jt.update(sql, user.getUsername(), user.getPassword(), user.getName(), user.getBirthday(), user.getSex(), user.getTelephone(), user.getEmail(), user.getStatus(), user.getCode());
        return count;
    }


    //查询数据库是否有此人
    @Override
    public User findUserByCode(String code) {
        User user = null;
        try {
            String sql = "select * from tab_user where code = ?";
             user = jt.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), code);
            return user;
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return  user;
    }
    //改变用户的激活码状态
    @Override
    public void changeStatus(User userByCode) {
        String sql = "update tab_user set status = 'Y' where uid = ? ";
        jt.update(sql,userByCode.getUid());

    }

    @Override
    public User findUserByusernameAndpassword(String username,String password) {
        User userlogin = null;
        try {
            String sql = "select * from tab_user where username = ? and password = ? ";
            userlogin = jt.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username,password);
            return userlogin;
        } catch (DataAccessException e) {
            //e.printStackTrace();
        }

        return  userlogin;
    }


}
