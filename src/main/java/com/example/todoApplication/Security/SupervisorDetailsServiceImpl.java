package com.example.todoApplication.Security;

import com.example.todoApplication.Entitys.Supervisor;
import com.example.todoApplication.Repositories.SupervisorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class SupervisorDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SupervisorRepository supervisorRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Supervisor supervisor = supervisorRepository.getSupervisorByEmail(username);
        if(supervisor == null){
            throw new UsernameNotFoundException("User not found !");
        }
        SupervisorDetails supervisorDetails = new SupervisorDetails(supervisor);
        return supervisorDetails;
    }
}
