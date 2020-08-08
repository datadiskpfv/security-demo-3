package uk.co.datadisk.securitydemo3.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class Provider1 implements AuthenticationProvider {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserDetails ud = userDetailsService.loadUserByUsername(name);

        if (ud.getPassword().matches(password) ) {
            System.out.println("Provider1  Username: " + name + "  Password: " + password + "   UserDetails Password: " + ud.getPassword());
            System.out.println("Provider1 password matches");
            return new UsernamePasswordAuthenticationToken(ud.getUsername(), ud.getPassword(), ud.getAuthorities());
        } else {
            System.out.println("Bad Credentials");
            throw new BadCredentialsException("System authentication failed");
        }
    }

    @Override
    public boolean supports(Class<?> aClass) {
        //return aClass.equals(UsernamePasswordAuthenticationToken.class);
        return UsernamePasswordAuthenticationToken.class.equals(aClass);
    }
}
