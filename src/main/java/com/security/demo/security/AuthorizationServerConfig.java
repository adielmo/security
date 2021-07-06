package com.security.demo.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import com.security.demo.token.CustomTokenEnhancer;

/**
 * 
 * @author Adielmo
 *
 */
@Profile("oauth-security")
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter{
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		/* Setando o Client q vai consumir a Api em memória */
clients.inMemory()
       .withClient("react")
          .secret("{noop}$2a$10$Tj6Pts4SIcVuoEHw/MDnPeOPXc8xBLei9zIKHS8J8SqYA5fE/Bdei")//Arthur
       .scopes("read", "write")
       .authorizedGrantTypes("refresh_token","password")
       .accessTokenValiditySeconds(1800)//1800 / 60 = 30 Segundos 
	   .refreshTokenValiditySeconds(3600 * 24)// 60 * 60 = 3600
.and()
		.withClient("angular")
		.secret("{noop}$2a$10$Tj6Pts4SIcVuoEHw/MDnPeOPXc8xBLei9zIKHS8J8SqYA5fE/Bdei")//Arthur
		.scopes("read")
		.authorizedGrantTypes("refresh_token","password")
		.accessTokenValiditySeconds(1800)//1800 / 60 = 30 Segundos 
		.refreshTokenValiditySeconds(3600 * 24);// 60 * 60 = 3600
	}

		
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), accessTokenConverter()));
		
		endpoints.tokenStore(tokenStore())
		         .accessTokenConverter(accessTokenConverter())
		         .tokenEnhancer(tokenEnhancerChain)//Setando nome do Usuario no Token
		         .reuseRefreshTokens(false)//Reutilização do refresh_token
		         .userDetailsService(this.userDetailsService)
		         .authenticationManager(this.authenticationManager);//autenticar um token 
	}

	@Bean
	public TokenEnhancer tokenEnhancer() {
		return new CustomTokenEnhancer();
	}


	@Bean //Gerando Token JWT
	public JwtAccessTokenConverter accessTokenConverter() {
     JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
     accessTokenConverter.setSigningKey("security");
		return accessTokenConverter;
	}

	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}
}
