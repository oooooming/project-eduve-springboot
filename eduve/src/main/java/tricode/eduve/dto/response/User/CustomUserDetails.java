package tricode.eduve.dto.response.User;

import org.springframework.security.core.GrantedAuthority;
import tricode.eduve.domain.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class CustomUserDetails implements UserDetails {

    private final User userEntity;

    public CustomUserDetails(User userEntity) {

        this.userEntity = userEntity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return userEntity.getRole();
            }
        });

        return collection;
    }

    @Override
    public String getPassword() {

        return userEntity.getPassword();
    }

    public String getEmail() {

        return userEntity.getEmail();
    }

    @Override
    public String getUsername() {

        return userEntity.getUsername();
    }

    public Long getUserId() {

        return userEntity.getUserId();
    }

    public String getName() {

        return userEntity.getName();
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
