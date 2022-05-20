package com.example.todoApplication.Security;

import com.example.todoApplication.Entitys.Supervisor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class SupervisorDetails implements UserDetails {


    private final String role = "SUPERVISOR";
    private Supervisor supervisor;


    public SupervisorDetails(Supervisor supervisor){
        this.supervisor = supervisor;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authorities = new SimpleGrantedAuthority(role);
        return List.of(authorities);
    }

    @Override
    public String getPassword() {
        return supervisor.getPassword();
    }

    @Override
    public String getUsername() {
        return supervisor.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    public boolean hasRole(String role){
        return role.equals(this.role);
    }
}
