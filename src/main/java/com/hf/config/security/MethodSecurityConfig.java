package com.hf.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled=true,jsr250Enabled=true,prePostEnabled=true)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration{

}
