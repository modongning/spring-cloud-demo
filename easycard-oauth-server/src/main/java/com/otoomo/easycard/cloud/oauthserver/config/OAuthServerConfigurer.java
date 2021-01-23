package com.otoomo.easycard.cloud.oauthserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.jwt.crypto.sign.MacSigner;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.sql.DataSource;

/**
 * 认证服务配置类
 * 主要配置客户端详情，token存储方式，token服务细节（如有效期），服务认证端点安全
 */
@Configuration
//开启认证服务功能
@EnableAuthorizationServer
public class OAuthServerConfigurer extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    //JWT签名加密秘钥
    private final static String SIGN_KEY = "zz^Zqm";

    @Autowired
    private MyAccessTokenConverter myAccessTokenConverter;

    /**
     * 配置认证服务的安全配置
     *
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        super.configure(security);
        security
                //允许客户端表单提交的方式认证
                .allowFormAuthenticationForClients()
                //开启/oauth/token的访问权限，默认是不允许的
                .tokenKeyAccess("permitAll()")
                //开启/oauth/check的访问权限，默认是不允许的
                .checkTokenAccess("permitAll()");
    }

    /**
     * 配置客户端验证的的细节
     * 包括客户端id,秘钥，资源，验证方式
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        super.configure(clients);
        /*
        从内存中客户端配置信息
         */
//        clients
//                //声明客户端信息存储在哪里
//                .inMemory()
//                //设置客户端ID
//                .withClient("easycard")
//                //设置客户端秘钥
//                .secret("123456")
//                //设置当前ID的客户端能访问的资源，此资源的id需要和资源服务器配置一样
//                .resourceIds("user-service")
//                //设置客户端认证和令牌颁发的方式，可以指定多个方式。具体使用哪个，由客户端请求的时候用从参数的方式指定
//                .authorizedGrantTypes("password", "refresh_token")
//                //设置客户端额的权限范围
//                .scopes("all");

        /*
        从数据库中获取客户端配置信息
         */
        clients.withClientDetails(jdbcClientDetailsService());
    }

    @Autowired
    private DataSource dataSource;

    /**
     * 创建jdbc客户端详情服务类，用户获取客户端在数据库中的信息
     *
     * @return
     */
    @Bean
    public JdbcClientDetailsService jdbcClientDetailsService() {
        JdbcClientDetailsService jdbcClientDetailsService = new JdbcClientDetailsService(dataSource);
        return jdbcClientDetailsService;
    }

    /**
     * 主要配置令牌存储方式和令牌的细节
     *
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        super.configure(endpoints);
        endpoints
                //配置认证服务token的存储方式
                .tokenStore(tokenStore())
                //配置token服务的细节管理类
                .tokenServices(getTokenService())
                //配置认证服务的身份验证器，具体的验证逻辑也是在这个管理器处理的
                .authenticationManager(authenticationManager)
                //设置认证服务允许的请求方式：GET,POST
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
    }

    /**
     * 创建token存储方式
     *
     * @return
     */
    private TokenStore tokenStore() {
//        return new InMemoryTokenStore();

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

        //设置自定义的token转换器实现类
        jwtAccessTokenConverter.setAccessTokenConverter(myAccessTokenConverter);

        return jwtAccessTokenConverter;
    }

    /**
     * 创建认证服务创建细节服务类
     *
     * @return
     */
    private AuthorizationServerTokenServices getTokenService() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        //设置支持刷新token
        defaultTokenServices.setSupportRefreshToken(true);
        //配置token的存储方式
        defaultTokenServices.setTokenStore(tokenStore());

        //设置token增强
        defaultTokenServices.setTokenEnhancer(jwtAccessTokenConverter());

        //配置access_token的有效时长（秒），一般设置2小时
        defaultTokenServices.setAccessTokenValiditySeconds(1 * 60 * 60 * 2);
        //设置refresh_token的有效时长（秒）
        defaultTokenServices.setRefreshTokenValiditySeconds(1 * 60 * 60 * 24 * 2);

        return defaultTokenServices;
    }
}
