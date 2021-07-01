package com.security.demo.token;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
public class RefreshTokenPostProcessor implements ResponseBodyAdvice<OAuth2AccessToken> {

	/**
	 * Class captura um type OAuth2AccessToken, qdo 
	 * retorna um AccessToken e RefreshToken
	 * Class q capturar um Response do type OAuth2AccessToken, com
	 * anotação @ControllerAdvice, para pegar refreshToken "body"
	 *  enviar para o Cookie, deixando o body RefreshToken null
	 */

	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return returnType.getMethod().getName().equals("postAccessToken");
	}

	@Override
	public OAuth2AccessToken beforeBodyWrite(OAuth2AccessToken body, MethodParameter returnType,
			MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType,
			ServerHttpRequest request, ServerHttpResponse response) {
		
    System.out.println("OAuth2AccessToken");
		DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) body;
		HttpServletRequest req = ((ServletServerHttpRequest) request).getServletRequest();
		HttpServletResponse res = ((ServletServerHttpResponse) response).getServletResponse();
		String refreshToken = body.getRefreshToken().getValue();

		adicionarRefreshTokenNoCookie(req, res, refreshToken);
		removerRefreshTokenDoBody(token);

		return body;
	}

	private void removerRefreshTokenDoBody(DefaultOAuth2AccessToken token) {

		token.setRefreshToken(null); /* Setando o valor do RefreshToken como null, ao retornar o body da requisição */
	}

	private void adicionarRefreshTokenNoCookie(HttpServletRequest req, HttpServletResponse res, String refreshToken) {
		Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken); /* Nome do Cookie é refreshToken */
		refreshTokenCookie.setHttpOnly(true); /* Acessivel somente em HTTP, envia p/Coockie */
		refreshTokenCookie.setSecure(false);// TODO: Mudar para True em produção
		refreshTokenCookie.setPath(req.getContextPath() + "/oauth/token");
		refreshTokenCookie.setMaxAge(2592000);//30 Dias

		res.addCookie(refreshTokenCookie);
	}

}
