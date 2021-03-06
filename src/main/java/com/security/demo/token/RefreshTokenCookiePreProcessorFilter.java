package com.security.demo.token;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Stream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.catalina.util.ParameterMap;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE) /* Alta prioridade */
public class RefreshTokenCookiePreProcessorFilter implements Filter {
	/**
	 * Class capturar todos  Request, e pasa pelo method doFilter
	 * Class q server como filtro de refreshToken,
	 * doFilter, pega o refreshToken q está no Cookie e enseri na requisição da URI
	 * "/oauth/token" para pegar um new accessToken
	 *Com a implementação Filter, ele pega todas as Request, enviadas via HTTP
	 */

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		//System.out.println("Method doFilter: " + req.toString());

		if ("/oauth/token".equalsIgnoreCase(req.getRequestURI())
				&& "refresh_token".equalsIgnoreCase(req.getParameter("grant_type"))
				&& req.getCookies() != null) {
			/* Pegando o refreshToken q está no cookie, e colocar na Request */
			String refreshToken = 	 
					Stream.of(req.getCookies())
			            .filter(cookie -> "refreshToken".equals(cookie.getName()))
			            .findFirst()
			            .map(Cookie::getValue)//cookie -> cookie.getValue()
			            .orElse(null);
			req = new MyServletRequestWrapper(req, refreshToken);
/*
 *  Optional<Cookie>  refreshToken= 
	for (Cookie cookie : req.getCookies()) {
		if (cookie.getName().equals("refreshToken")) {

			String refreshToken = cookie.getValue();
			

				}
			}
			*/
		}
		chain.doFilter(req, response);
	}

	static class MyServletRequestWrapper extends HttpServletRequestWrapper {

		private String refreshToken;

		public MyServletRequestWrapper(HttpServletRequest request, String refreshToken) {
			super(request);
			this.refreshToken = refreshToken;

		}

		@Override
		public Map<String, String[]> getParameterMap() {

			ParameterMap<String, String[]> map = new ParameterMap<>(getRequest().getParameterMap());
			map.put("refresh_token", new String[] { refreshToken });
			map.setLocked(true);
			return map;
		}

	}

}
