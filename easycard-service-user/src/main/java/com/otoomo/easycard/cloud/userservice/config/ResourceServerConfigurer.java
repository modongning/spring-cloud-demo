package com.otoomo.easycard.cloud.userservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.jwt.crypto.sign.MacSigner;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * 资源服务器配置
 */
@Configuration
//开启资源服务器
@EnableResourceServer
//开启web服务访问安全
@EnableWebSecurity
public class ResourceServerConfigurer extends ResourceServerConfigurerAdapter {

    //JWT签名加密秘钥
    private final static String SIGN_KEY = "zz^Zqm";

    @Autowired
    private MyAccessTokenConverter myAccessTokenConverter;

    /**
     * 配置token验证细节
     * 主要用于验证token的，通过调用远程的认证服务/JWT本地解析，验证token的有效性
     *
     * @param resources
     * @throws Exception
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        /*
        这里是通过接口远程调用认证服务验证token的。
         */
//        //设置资源服务的资源ID
//        resources.resourceId("user-service");
//        //定于远程路由服务
//        RemoteTokenServices remoteTokenServices = new RemoteTokenServices();
//        //设置远程验证链接
//        remoteTokenServices.setCheckTokenEndpointUrl("http://127.0.0.1:9999/auth/check_token");
//        //设置客户端ID
//        remoteTokenServices.setClientId("easycard");
//        //设置客户端秘钥
//        remoteTokenServices.setClientSecret("123456");
//
//        //给资源服务器设置远程认证服务细节信息
//        resources.tokenServices(remoteTokenServices);

        /*
        使用jwt方式验证token
         */
        resources
                //设置资源服务的资源ID
                .resourceId("user-service")
                //指定验证token
                .tokenStore(tokenStore())
                //无状态设置
                .stateless(true);
    }

    /**
     * 服务器中，并不是所有接口都需要token验证的。
     * 所以就需要配置需要token验证的接口信息。
     *
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                //设置session创建策略
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                //设置需要验证token的请求接口
                .authorizeRequests()
                .antMatchers("/user/**").authenticated()
                //其他请求不验证
                .anyRequest().permitAll();
    }

    /**
     * 创建token存储方式
     *
     * @return
     */
    private TokenStore tokenStore() {
        //使用jwt的方式创建令牌
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    /**
     * 创建jwt访问token转换器
     *
     * @return
     */
    private JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        //设置签名秘钥
        jwtAccessTokenConverter.setSigningKey(SIGN_KEY);
        //设置验证秘钥，保持和签名秘钥一致
        jwtAccessTokenConverter.setVerifier(new MacSigner(SIGN_KEY));

        //设置自定义token转换器
        jwtAccessTokenConverter.setAccessTokenConverter(myAccessTokenConverter);

        return jwtAccessTokenConverter;
    }
}
