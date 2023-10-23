package com.web.user_management.Service;

import com.web.user_management.Model.User;
import com.web.user_management.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public void saveUser(User user) {
        String passwordencode = passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordencode);
        userRepository.save(user);
    }

    @Override
    public List<User> listUser() {
        return userRepository.findAll();
    }

    @Override
    public boolean usernameExist(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean emailExist(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public void updateById(long id, User user) {
        User userData = userRepository.findById(id).get();
        if (Objects.nonNull(user.getUsername()) != "".equalsIgnoreCase(user.getUsername())) {
            userData.setUsername(user.getUsername());
        }
        if (Objects.nonNull(user.getEmail()) && !"".equalsIgnoreCase(user.getEmail())) {
            userData.setEmail(user.getEmail());
        }
        userRepository.save(userData);
    }

    @Override
    public User findUserById(long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public void deleteUserById(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> listUser(String keyword,String role) {
        return userRepository.findByUsernameContaining(keyword,role);
    }


    @Override
    public List<User> findByRole() {
        return userRepository.findByRole("USER");
    }
}
