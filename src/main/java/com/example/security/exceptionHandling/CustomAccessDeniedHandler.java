package com.example.security.exceptionHandling;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setHeader("custom authentication error","custom authentication error");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        //  response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
        response.setContentType("application/json; charset=utf-8");
        String jsonResponse="{\"error in 403 \":\""+accessDeniedException.getMessage()+"\"}";
        response.getWriter().write(jsonResponse);
        response.getWriter().flush();
    }
}
