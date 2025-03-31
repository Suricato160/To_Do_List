package com.webtodolist.service;


import com.webtodolist.entity.User;
import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> findByUsername(String username);
    
    Optional<User> findByEmail(String email);
    
    boolean existsByUsername(String username);
    
    boolean existsByEmail(String email);
    
    User saveUser(User user);
    
    void deleteUser(Long id);
    
    List<User> findAllUsers();
    
    Optional<User> findById(Long id);
}
