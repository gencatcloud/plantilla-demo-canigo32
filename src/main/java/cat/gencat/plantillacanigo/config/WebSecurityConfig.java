/*******************************************************************************
 * Copyright 2016 Generalitat de Catalunya.
 *
 * The contents of this file may be used under the terms of the EUPL, Version 1.1 or - as soon they will be approved by the European Commission - subsequent
 * versions of the EUPL (the "Licence"); You may not use this work except in compliance with the Licence. You may obtain a copy of the Licence at:
 * http://www.osor.eu/eupl/european-union-public-licence-eupl-v.1.1 Unless required by applicable law or agreed to in writing, software distributed under the
 * Licence is distributed on an "AS IS" basis, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the Licence for the specific
 * language governing permissions and limitations under the Licence.
 *
 * Original authors: Centre de Suport Canigó Contact: oficina-tecnica.canigo.ctti@gencat.cat
 *******************************************************************************/
package cat.gencat.plantillacanigo.config;

import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.http.HttpStatus;
import javax.inject.Named;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.web.bind.annotation.RequestMethod;

import cat.gencat.ctti.canigo.arch.core.config.PropertiesConfiguration;
import cat.gencat.ctti.canigo.arch.security.rest.authentication.gicar.ProxyUsernamePasswordAuthenticationFilter;
import cat.gencat.ctti.canigo.arch.security.rest.authentication.jwt.JwtAuthenticationFilter;
import cat.gencat.ctti.canigo.arch.security.rest.authentication.jwt.JwtTokenHandler;
import cat.gencat.ctti.canigo.arch.security.rest.authentication.service.AuthenticationService;
import cat.gencat.ctti.canigo.arch.security.rest.authentication.service.impl.JwtAuthenticationService;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	@Lazy
	private AuthenticationEntryPoint restAuthenticationEntryPoint;

	@Autowired
	private PropertiesConfiguration propertiesConfiguration;

	@Autowired
	@Lazy
	private AuthenticationManager authenticationManager;

	@Autowired
	@Lazy
	private AuthenticationSuccessHandler restAuthenticationSuccessHandler;

	@Autowired
	@Lazy
	private AuthenticationFailureHandler restAuthenticationFailureHandler;

	@Autowired
	@Lazy
	private AccessDeniedHandler restAccessDeniedHandler;

	@Autowired
	@Lazy
	private DataSource dataSource;

	@Bean
	@Autowired
	public ProxyUsernamePasswordAuthenticationFilter proxyUsernamePasswordAuthenticationFilter() {
		final ProxyUsernamePasswordAuthenticationFilter proxyUsernamePasswordAuthenticationFilter = new ProxyUsernamePasswordAuthenticationFilter("/api/login", RequestMethod.POST.toString());
		proxyUsernamePasswordAuthenticationFilter.setSiteminderAuthentication(isSiteminderAuthentication());
		proxyUsernamePasswordAuthenticationFilter.setAuthenticationManager(authenticationManager);
		proxyUsernamePasswordAuthenticationFilter.setAuthenticationSuccessHandler(restAuthenticationSuccessHandler);
		proxyUsernamePasswordAuthenticationFilter.setAuthenticationFailureHandler(restAuthenticationFailureHandler);
		return proxyUsernamePasswordAuthenticationFilter;
	}

	@Override
	protected void configure(final HttpSecurity http) throws Exception {

		http.authorizeRequests().antMatchers("/api/auth").permitAll().antMatchers("/images/*/**", "/css/*/**", "/js/*/**", "/fonts/*/**").permitAll().antMatchers("/api/info/**", "/api/logs/**").hasRole("ADMIN").antMatchers("/api/equipaments/**").hasRole("USER");

		http.exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint);
		http.exceptionHandling().accessDeniedHandler(restAccessDeniedHandler);
		http.csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(jwtAuthenticationFilter(), AbstractPreAuthenticatedProcessingFilter.class);
	}

	private boolean isSiteminderAuthentication() {
		return false;
	}

	@Bean
	@Named("jwtAuthenticationService")
	public AuthenticationService jwtAuthenticationService() {
		final JwtAuthenticationService jwtAuthenticationService = new JwtAuthenticationService();
		jwtAuthenticationService.setSiteminderAuthentication(getSiteminderAuthentication());
		jwtAuthenticationService.setTokenResponseHeaderName(getTokenResponseHeaderName());
		jwtAuthenticationService.setHeaderAuthName(getHeaderAuthName());

		return jwtAuthenticationService;
	}

	@Bean
	@Named("jwtAuthenticationFilter")
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		final JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter();
		jwtAuthenticationFilter.setHeaderAuthName(getHeaderAuthName());
		jwtAuthenticationFilter.setStartToken(getStartToken());
		jwtAuthenticationFilter.setTokenResponseHeaderName(getTokenResponseHeaderName());
		return jwtAuthenticationFilter;
	}

	@Bean
	@Named("jwtTokenHandler")
	public JwtTokenHandler jwtTokenHandler() {
		final JwtTokenHandler JwtTokenHandler = new JwtTokenHandler();
		JwtTokenHandler.setExpiration(getExpiration());
		JwtTokenHandler.setSecret(getSecret());
		return JwtTokenHandler;
	}

	private String getSecret() {
		final String secret = propertiesConfiguration.getProperty("jwt.secret");
		return secret != null ? secret : "canigo";
	}

	private Long getExpiration() {
		final Long expiration = new Long(propertiesConfiguration.getProperty("jwt.expiration"));
		return expiration != null ? expiration : 3600L;
	}

	private String getStartToken() {
	final String startToken = propertiesConfiguration.getProperty("jwt.header.startToken");
		return startToken != null ? startToken : "Bearer";
	}

	private String getHeaderAuthName() {
		final String headerAythName = propertiesConfiguration.getProperty("jwt.header");
		return headerAythName != null ? headerAythName : "Authentication";
	}

	private String getTokenResponseHeaderName() {
		final String tokenResponseHeaderName = propertiesConfiguration.getProperty("jwt.tokenResponseHeaderName");
		return tokenResponseHeaderName != null ? tokenResponseHeaderName : "jwtToken";
	}

	private boolean getSiteminderAuthentication() {
		final Boolean siteminder = new Boolean(propertiesConfiguration.getProperty("jwt.siteminderAuthentication"));
		return siteminder != null ? siteminder : false;
	}

}
