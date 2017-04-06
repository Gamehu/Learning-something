package org.elane.filter;

import java.util.ArrayList;
import java.util.List;

import org.mitre.oauth2.introspectingfilter.service.IntrospectionAuthorityGranter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * @author zoro
 * 重写用户授权：根据用户的scope授权用户信息
 */
public class ScopeAuthoritiesGranter implements IntrospectionAuthorityGranter {
	
	private List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_API");

	/* (non-Javadoc)
	 * @see org.mitre.oauth2.introspectingfilter.IntrospectionAuthorityGranter#getAuthorities(net.minidev.json.JSONObject)
	 */
	@Override
	public List<GrantedAuthority> getAuthorities(JsonObject introspectionResponse) {
		List<GrantedAuthority> auth = new ArrayList<>(getAuthorities());
		
		if (introspectionResponse.has("scope")) {
			for(JsonElement element : introspectionResponse.get("scope").getAsJsonArray()){
				auth.add(new SimpleGrantedAuthority("OAUTH_SCOPE_" + element.getAsString()));
			}
		}
		
		return auth;
	}

	/**
	 * @return the authorities
	 */
	public List<GrantedAuthority> getAuthorities() {
		return authorities;
	}

	/**
	 * @param authorities the authorities to set
	 */
	public void setAuthorities(List<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

}
