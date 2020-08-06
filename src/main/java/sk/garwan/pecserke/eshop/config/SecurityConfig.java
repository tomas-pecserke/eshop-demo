package sk.garwan.pecserke.eshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import sk.garwan.pecserke.eshop.DtoMapper;
import sk.garwan.pecserke.eshop.identity.JpaUserDetailsService;
import sk.garwan.pecserke.eshop.identity.model.SecurityUserDto;
import sk.garwan.pecserke.eshop.identity.persistance.User;
import sk.garwan.pecserke.eshop.identity.persistance.UserRepository;

@Configuration
@EnableGlobalMethodSecurity(
    prePostEnabled = true,
    securedEnabled = true,
    jsr250Enabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserRepository userRepository;
    private final DtoMapper<User, SecurityUserDto> securityUserMapper;

    public SecurityConfig(
        UserRepository userRepository,
        DtoMapper<User, SecurityUserDto> securityUserMapper
    ) {
        this.userRepository = userRepository;
        this.securityUserMapper = securityUserMapper;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .sessionManagement().disable()
            .cors()
            .and()
            .httpBasic();
    }

    @Override
    protected UserDetailsService userDetailsService() {
        return new JpaUserDetailsService(userRepository, securityUserMapper);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
