package ums.axon.security;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.GenericCommandMessage;
import org.axonframework.commandhandling.StructuralCommandValidationFailedException;
import org.axonframework.commandhandling.callbacks.FutureCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.stereotype.Component;

import ums.axon.command.AuthenticateUserCommand;
import ums.axon.query.UserEntry;

import java.util.Collection;
import java.util.HashSet;
import java.util.concurrent.ExecutionException;

@Component
public class TraderAuthenticationProvider implements AuthenticationProvider {

    private final static Collection<GrantedAuthority> userAuthorities;

    static {
        userAuthorities = new HashSet<GrantedAuthority>();
        userAuthorities.add(new GrantedAuthorityImpl("ROLE_USER"));
    }

    @Autowired
    private CommandBus commandBus;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (!supports(authentication.getClass())) {
            return null;
        }
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        String username = token.getName();
        String password = String.valueOf(token.getCredentials());
        FutureCallback<UserEntry> accountCallback = new FutureCallback<UserEntry>();
        AuthenticateUserCommand command = new AuthenticateUserCommand(username, password.toCharArray());
        try {
            commandBus.dispatch(new GenericCommandMessage<AuthenticateUserCommand>(command), accountCallback);
        } catch (StructuralCommandValidationFailedException e) {
            return null;
        }
        UserEntry account;
        try {
            account = accountCallback.get();
            if (account == null) {
                throw new BadCredentialsException("Invalid username and/or password");
            }
        } catch (InterruptedException e) {
            throw new AuthenticationServiceException("Credentials could not be verified", e);
        } catch (ExecutionException e) {
            throw new AuthenticationServiceException("Credentials could not be verified", e);
        }

        UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(account,
                authentication.getCredentials(), userAuthorities);
        result.setDetails(authentication.getDetails());
        return result;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
