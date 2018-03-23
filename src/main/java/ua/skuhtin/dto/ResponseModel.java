package ua.skuhtin.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import ua.skuhtin.model.Roles;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseModel implements Serializable {
    private String login;
    private Roles userRoles;
    private String message;

    public ResponseModel() {
    }

    public ResponseModel(String login, Roles userRoles) {
        this.login = login;
        this.userRoles = userRoles;
    }

    public ResponseModel(String login, Roles userRoles, String message) {
        this.login = login;
        this.userRoles = userRoles;
        this.message = message;
    }

    public ResponseModel(String message) {
        this.message = message;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Roles getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Roles userRoles) {
        this.userRoles = userRoles;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
