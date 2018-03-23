package ua.skuhtin.model;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "users")
@DynamicUpdate
public class Users {
    @Id
    @SequenceGenerator(name = "users_seq", catalog = "sequences", schema = "public", sequenceName = "users_seq_pk", allocationSize = 1)
    @GeneratedValue(generator = "users_seq", strategy = GenerationType.SEQUENCE)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "role_id")
    private Long roleId;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "role_id", insertable = false, updatable = false)
    private Roles roles;

    public Users() {
    }

    public Users(String login, String  password, Boolean enabled, Long roleId) {
        this.login = login;
        this.password = password;
        this.enabled = enabled;
        this.roleId = roleId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String  password) {
        this.password = password;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Roles getRoles() {
        return roles;
    }
}
