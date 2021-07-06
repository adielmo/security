package com.security.demo.token;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import com.security.demo.util.UsuarioSistema;

public class CustomTokenEnhancer implements TokenEnhancer {

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
UsuarioSistema usuarioSistema = (UsuarioSistema) authentication.getPrincipal();

Map<String, Object> mapNomeUsuario = new HashMap<>();
mapNomeUsuario.put("nome", usuarioSistema.getUsuario().getNome());

     ((DefaultOAuth2AccessToken)accessToken).setAdditionalInformation(mapNomeUsuario);
return accessToken;
	}

}
