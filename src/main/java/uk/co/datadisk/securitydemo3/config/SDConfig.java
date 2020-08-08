package uk.co.datadisk.securitydemo3.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import uk.co.datadisk.securitydemo3.security.Filter1;
import uk.co.datadisk.securitydemo3.security.Filter2;
import uk.co.datadisk.securitydemo3.security.Filter3;
import uk.co.datadisk.securitydemo3.security.Provider1;

@EnableWebSecurity
public class SDConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private Provider1 provider1;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(provider1);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic();

        http.authorizeRequests()
                .antMatchers("/index", "/index/**").permitAll()
                .anyRequest().authenticated();

        http    .addFilterBefore(new Filter1(), UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(new Filter2(), UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(new Filter3(), UsernamePasswordAuthenticationFilter.class);
    }

    // Override the password encoder
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    // Override the UserDetailsService
    @Bean
    @Override
    public UserDetailsService userDetailsService() {

        // Admin Role
        UserDetails admin = User.withUsername("admin")
                .password("12345").roles("ADMIN").build();

        // User Role
        UserDetails user = User.withUsername("user")
                .password("12345").roles("USER").build();

        // Does extends UserDetailsService
        InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
        userDetailsManager.createUser(admin);
        userDetailsManager.createUser(user);

        return userDetailsManager;
    }
}
