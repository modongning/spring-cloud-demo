package com.otoomo.easycard.cloud.userservice.config;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 自定义AccessTokenConverter转换器，可以从token中提取自定义的信息
 */
@Component
public class MyAccessTokenConverter extends DefaultAccessTokenConverter {

    /**
     * 重写提取身份认证信息方法
     *
     * @param map
     * @return
     */
    @Override
    public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
        OAuth2Authentication oAuth2Authentication = super.extractAuthentication(map);

        //把map对象放入身份认证对象中，认证对象可以在controller中获取到。
        oAuth2Authentication.setDetails(map);

        return oAuth2Authentication;
    }
}
