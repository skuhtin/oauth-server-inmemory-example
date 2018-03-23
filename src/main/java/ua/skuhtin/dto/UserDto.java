package ua.skuhtin.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class UserDto implements Serializable{

    @ApiModelProperty(required = true)
    private String login;
    @ApiModelProperty(required = true)
    private String password;
    @ApiModelProperty(required = true)
    private boolean enabled;
    @ApiModelProperty(required = true)
    private Long roleId;

    public UserDto() {
    }

    public UserDto(String login, String password, boolean enabled, Long roleId) {
        this.login = login;
        this.password = password;
        this.enabled = enabled;
        this.roleId = roleId;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
