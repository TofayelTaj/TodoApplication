package com.example.todoApplication.Security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

        Object userDetails =   authentication.getPrincipal();

        String path = request.getContextPath();
        if(userDetails instanceof  ApplicationUserDetails)
            path = "/user/dashboard";
        else
           path = "/supervisor/dashboard";


        response.sendRedirect(path);

    }
}
