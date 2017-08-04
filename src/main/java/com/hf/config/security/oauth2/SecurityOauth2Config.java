package com.hf.config.security.oauth2;

import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.ClientDetailsUserDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

//@Configuration
public class SecurityOauth2Config {

    @Bean
    public JdbcTokenStore jdbcTokenStore(DataSource dataSource){
        return new JdbcTokenStore(dataSource);
    }

    @Bean
    public DefaultTokenServices tokenServices(TokenStore tokenStore){
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(tokenStore);
        tokenServices.setSupportRefreshToken(true);
        return tokenServices;
    }

    @Bean
    public JdbcClientDetailsService clientDetailsService(DataSource dataSource){
        return new JdbcClientDetailsService(dataSource);
    }

    @Bean
    public ClientDetailsUserDetailsService clientDetailsUserDetailsService(ClientDetailsService clientDetailsService){
        return new ClientDetailsUserDetailsService(clientDetailsService);
    }

    @Bean
    public OAuth2AuthenticationEntryPoint oAuth2AuthenticationEntryPoint(){
        return new OAuth2AuthenticationEntryPoint();
    }
}
