package com.example.todoApplication.Security;

import com.example.todoApplication.Entitys.User;
import com.example.todoApplication.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User  user = userRepository.getUserByEmail(username);
        if(user == null){
            throw new UsernameNotFoundException("User not found !");
        }
        ApplicationUserDetails applicationUserDetails = new ApplicationUserDetails(user);
        return applicationUserDetails;
    }
}
