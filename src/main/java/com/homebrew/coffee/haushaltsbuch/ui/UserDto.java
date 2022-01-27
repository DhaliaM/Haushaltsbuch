package com.homebrew.coffee.haushaltsbuch.ui;

/**
 * Ein Dto um Nutzerdaten zu übertragen.
 */
public class UserDto {

    private String userName;
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}


