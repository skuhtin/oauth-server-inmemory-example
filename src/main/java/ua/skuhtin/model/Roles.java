package ua.skuhtin.model;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Roles {
    @Id
    @SequenceGenerator(name = "roles_seq", schema = "public", catalog = "sequences", sequenceName = "roles_seq_pk", allocationSize = 1)
    @GeneratedValue(generator = "roles_seq", strategy = GenerationType.SEQUENCE)
    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "role")
    private String role;

    public Roles() {
    }

    public Roles(String role) {
        this.role = role;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
