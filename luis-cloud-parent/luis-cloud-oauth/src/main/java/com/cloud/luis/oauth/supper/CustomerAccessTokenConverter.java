package com.cloud.luis.oauth.supper;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.util.StringUtils;

import com.cloud.luis.oauth.model.SecurityUserDetail;

public class CustomerAccessTokenConverter extends DefaultAccessTokenConverter{
    
    public CustomerAccessTokenConverter() {
        super.setUserTokenConverter(new CustomerUserAuthenticationConverter());
    }

    private class CustomerUserAuthenticationConverter extends DefaultUserAuthenticationConverter {

        @Override
        public Map<String, ?> convertUserAuthentication(Authentication authentication) {
            SecurityUserDetail jwtUser = (SecurityUserDetail) authentication.getPrincipal();
            LinkedHashMap<String, Object> response = new LinkedHashMap<>();
            response.put("userId", jwtUser.getUserId());
            if (authentication.getAuthorities() != null && !authentication.getAuthorities().isEmpty()) {
                response.put("authorities", AuthorityUtils.authorityListToSet(authentication.getAuthorities()));
            }
            return response;
        }

        @SuppressWarnings("unchecked")
        @Override
        public Authentication extractAuthentication(Map<String, ?> map) {
            
            List<String> authList = (List<String>) map.get("authorities");
            Collection<? extends GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(StringUtils
                        .collectionToCommaDelimitedString((Collection<?>) authList));
            Object principal = new SecurityUserDetail(Long.valueOf(map.get("userId").toString()), String.valueOf(map.get("user_name")), null, true, true, true, true, authorities);
                
           return new UsernamePasswordAuthenticationToken(principal, "N/A", authorities);
            
        }

        
    }

}
