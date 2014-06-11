package ums.plus.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service("UserLoginDetailsService")
public class UserLoginDetailsService implements UserDetailsService{

    @Autowired
    private IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ums.plus.domain.User user = userService.findUserByUsername(username);
        return new UserLoginDetails(user);
    }

    @SuppressWarnings("serial")
    public static class UserLoginDetails implements UserDetails {

        public static final String SCOPE_READ = "read";

        public static final String SCOPE_WRITE = "write";

        public static final String ROLE_USER = "ROLE_USER";

        private Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();

        private ums.plus.domain.User user;

        public UserLoginDetails(ums.plus.domain.User user) {
            Assert.notNull(user, "the provided user reference can't be null");
            this.user = user;
            for (String ga : Arrays.asList(ROLE_USER, SCOPE_READ, SCOPE_WRITE)) {
                this.grantedAuthorities.add(new SimpleGrantedAuthority(ga));
            }
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return this.grantedAuthorities;
        }

        @Override
        public String getPassword() {
            return user.getPassword();
        }

        @Override
        public String getUsername() {
            return user.getUsername();
        }

        @Override
        public boolean isAccountNonExpired() {
            return isEnabled();
        }

        @Override
        public boolean isAccountNonLocked() {
            return isEnabled();
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return isEnabled();
        }

        @Override
        public boolean isEnabled() {
            return true;
            //NOT support yet
//            return user.isEnabled();
        }

        public  ums.plus.domain.User getUser() {
            return this.user;
        }
    }

    
     
}
