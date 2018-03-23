package ua.skuhtin.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import ua.skuhtin.model.Roles;
import ua.skuhtin.model.Users;

import java.util.Collection;

public class SecurityUser extends User {

    private Long userId;
    private Roles role;

    public SecurityUser(Users user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getLogin(), user.getPassword(), authorities);
        this.userId = user.getUserId();
        this.role = user.getRoles();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }
}
