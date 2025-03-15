package com.example.security.filter;

import com.example.security.constant.ApplicationConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class JWTTokenValidatorFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        String jwtToken = request.getHeader(ApplicationConstant.JWT_HEADER);
        if (null != jwtToken) {
            try {
                Environment env = getEnvironment();
                if (env != null) {
                    String secret = env.getProperty(ApplicationConstant.JWT_SECRET_KEY,
                            ApplicationConstant.JWT_DEFAULT_SECRET_KEY);
                    SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
if(null!=secretKey){

   Claims claims= Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(jwtToken.trim())
            .getPayload();

   String username=String.valueOf(claims.get("username"));
   String authorities=String.valueOf(claims.get("authorities"));
    Authentication authentication=new UsernamePasswordAuthenticationToken(username,null
            , AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
    SecurityContextHolder.getContext().setAuthentication(authentication);

}

                }
            } catch (Exception e) {
                throw new BadCredentialsException("Invalid JWT");
            }
        }

        filterChain.doFilter(request, response);
    }

    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getServletPath().equals("/user");
    }
}
