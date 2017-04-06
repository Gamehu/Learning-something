package org.elane.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;


/**
 * client Authentication Filter class
 * 
 * @author zoro
 * 
 */
public class ClientAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
	
	protected final static String FILTER_PROCESSES_URL = "/";
	
	private ClientAccessTokenManger clientAccessTokenManger;
	
	public ClientAccessTokenManger getClientAccessTokenManger() {
		return clientAccessTokenManger;
	}

	public void setClientAccessTokenManger(
			ClientAccessTokenManger clientAccessTokenManger) {
		this.clientAccessTokenManger = clientAccessTokenManger;
	}

	
	protected ClientAuthenticationFilter() {
		super(FILTER_PROCESSES_URL);
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String client_access_token = clientAccessTokenManger.readAccessToken();
        if(!StringUtils.isEmpty(client_access_token)){
        	chain.doFilter(request, response);
        }else{
            unsuccessfulAuthentication(request, response, new AuthenticationException("get client access-token faild") {
			});
        	return;
        }
    }


	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException,
			IOException, ServletException {
		return null;
	}

}
