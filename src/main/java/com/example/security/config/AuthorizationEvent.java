package com.example.security.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.authorization.event.AuthorizationDeniedEvent;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthorizationEvent {



    @EventListener
    public void onFailer(AuthorizationDeniedEvent deniedEvent) {
        log.info("Authentication failure {} dua to {} "
                ,deniedEvent.getAuthentication().get().getName()
                , deniedEvent.getAuthorizationResult());
    }
}
