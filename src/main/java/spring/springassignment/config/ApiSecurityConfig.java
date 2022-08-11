package spring.springassignment.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class ApiSecurityConfig extends WebSecurityConfigurerAdapter {
//    @Value("${auth0.audience}")
//    private String audience;
//    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
//    private String issuer;
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // tạo filter cho login, cấu hình theo link đăng nhập
        ApiAuthenticationFilter apiAuthenticationFilter = new ApiAuthenticationFilter(authenticationManagerBean());
        apiAuthenticationFilter.setFilterProcessesUrl("/api/v1/accounts/login");
        http.cors().and().csrf().disable();
        http.authorizeRequests()
                .antMatchers("/api/v1/products",
                        "/api/v1/orders",
                        "/api/v1/account/login",
                        "/api/v1/account/register")
                .permitAll();
        http.authorizeRequests()
                .antMatchers("/api/v1/accounts/users").hasAnyAuthority("user");
        http.authorizeRequests()
                .antMatchers( "/api/v1/accounts/admins").hasAnyAuthority("admin");

        // auth0
//        http.authorizeRequests()
//                .antMatchers(HttpMethod.GET, "/api/menu/items/**").permitAll()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .oauth2ResourceServer()
//                .jwt();
        http.addFilter(apiAuthenticationFilter);
        http.addFilterBefore(new ApiAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

//    JwtDecoder jwtDecoder() {
//        OAuth2TokenValidator<Jwt> wityAudience = new AudienceValidator(audience);
//        OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(issuer);
//        OAuth2TokenValidator<Jwt> validator = new DelegatingOAuth2TokenValidator<>(wityAudience, withIssuer);
//
//        NimbusJwtDecoder jwtDecoder = (NimbusJwtDecoder) JwtDecoders.fromIssuerLocation(issuer);
//        jwtDecoder.setJwtValidator(validator);
//        return jwtDecoder;
//    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }
}
