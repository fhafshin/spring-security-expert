package com.example.security.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthenticationEvent {

    @EventListener
    public void onSuccess(AuthenticationSuccessEvent successEvent) {
log.info("Authentication success {}",successEvent.getAuthentication().getName());

    }

    @EventListener
    public void onFailer(AbstractAuthenticationFailureEvent failureEvent) {
        log.info("Authentication failure {} dua to {} "
                ,failureEvent.getAuthentication().getName()
                , failureEvent.getException().getMessage());
    }
}
