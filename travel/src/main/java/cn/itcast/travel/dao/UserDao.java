package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

import java.util.List;

public interface UserDao {
    Boolean findUser(User user);

    int checkUsername(String username);

    int save(User user);

    User findUserByCode(String code);

    void changeStatus(User userByCode);

    User findUserByusernameAndpassword(String username,String password);
}
