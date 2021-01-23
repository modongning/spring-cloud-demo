package com.otoomo.easycard.cloud.oauthserver.config;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 实现往jwt信息中添加自定义的字段
 * DefaultAccessTokenConverter 是具体处理access_token的默认转换器，
 * 只需要重写convertAccessToken()函数即可实现添加自己的参数到jwt中
 */
@Component
public class MyAccessTokenConverter extends DefaultAccessTokenConverter {
    /**
     * 重写方法，放入自定义的信息。
     * @param token
     * @param authentication
     * @return
     */
    @Override
    public Map<String, ?> convertAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        Map<String, String> convertAccessToken = (Map<String, String>) super.convertAccessToken(token, authentication);
        //TODO 设置客户端ip
        convertAccessToken.put("clientIp", "120.0.0.1");
        return convertAccessToken;
    }
}
