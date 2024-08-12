package bg.codeacademy.cakeShop.security;

import bg.codeacademy.cakeShop.model.PersonalData;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AuthenticatedUser implements UserDetails {
    private final PersonalData personalData;

    public AuthenticatedUser(PersonalData user) {
        this.personalData = user;
    }

    @Override
    public String getUsername() {
        return personalData.getUserName();
    }
    public int getId() {
        return personalData.getId();
    }
    @Override
    public String getPassword() {
        return personalData.getUserPassword();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + personalData.getUserRole()));
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
