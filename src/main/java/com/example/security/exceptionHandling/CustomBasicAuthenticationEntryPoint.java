package com.example.security.exceptionHandling;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class CustomBasicAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setHeader("custom authentication error","custom authentication error");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      //  response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
        response.setContentType("application/json; charset=utf-8");
        String jsonResponse="{\"error in \":\""+authException.getMessage()+"\"}";
                response.getWriter().write(jsonResponse);
                response.getWriter().flush();

    }


}
