package cn.itcast.travel.service;

import cn.itcast.travel.domain.User;

public interface UserService {
    Boolean registUser(User user);

    String checkUsername(String username);

    Boolean active(String code);

    User findUserByusernameAndpassword(String username,String password);
}
