package org.wangwh.code.auth.uaa.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.sql.DataSource;

@Configuration
@EnableAuthorizationServer
@Slf4j
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private TokenStore tokenStore;

	@Autowired
	private JwtAccessTokenConverter accessTokenConverter;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private ClientDetailsService clientDetailsService;

	@Autowired
	private UserDetailsService userDetailsService;

	// 客户端详情
	// a configurer that defines the client details service.
	// Client details can be initialized,
	// or you can just refer to an existing store
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.withClientDetails(clientDetailsService);
		/*clients.inMemory()
				.withClient("app")
				.secret("secret")
				.resourceIds("order")
				.scopes("read","writer")
				.authorizedGrantTypes("password","authorization_code","refresh_code")
				.autoApprove(true)
				.redirectUris("http://www.baidu.com");*/
	}


	// 配置访问策略
	// defines the security constraints on the token endpoint.
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.allowFormAuthenticationForClients()// 允许表单认证
				.checkTokenAccess("permitAll()") // 校验Token 访问策略
				.tokenKeyAccess("permitAll()") ;  // 令牌密钥策略
	}

	// 配置令牌服务
	// defines the authorization and token endpoints and the token services
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		log.debug("配置令牌端点及令牌服务");
		endpoints.authenticationManager(authenticationManager)// 密码模式需要
				 .authorizationCodeServices(authorizationCodeServices()) // 授权码生成
				 .userDetailsService(userDetailsService) // 刷新令牌需要
				 .accessTokenConverter(accessTokenConverter)
				 .tokenServices(tokenServices())
				 .tokenStore(tokenStore)
				 .setClientDetailsService(clientDetailsService);
	}

	//授权码生成策略
	@Bean
	public AuthorizationCodeServices authorizationCodeServices(){
		return new InMemoryAuthorizationCodeServices();
	}

	@Bean
	public AuthorizationServerTokenServices tokenServices(){
		DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setSupportRefreshToken(true);
		defaultTokenServices.setRefreshTokenValiditySeconds(1*60*60);
		defaultTokenServices.setRefreshTokenValiditySeconds(2*60*60);
		defaultTokenServices.setTokenEnhancer(accessTokenConverter);
		defaultTokenServices.setTokenStore(tokenStore);
		return defaultTokenServices;
	}

	//client for db
	@Bean
	public JdbcClientDetailsService clientDetailsService(DataSource dataSource){
		return new JdbcClientDetailsService(dataSource);
	}

}
