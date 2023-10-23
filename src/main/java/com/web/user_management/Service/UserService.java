package com.web.user_management.Service;


import com.web.user_management.Model.User;

import java.util.List;

public interface UserService  {

    void saveUser(User user);
    List<User> listUser();

    public boolean usernameExist(String username);

    public  boolean emailExist(String email);

    void updateById(long id, User user);

     User findUserById(long id);

     void deleteUserById(long id);

    List<User> listUser(String keyword,String role);


     List<User> findByRole();


}
