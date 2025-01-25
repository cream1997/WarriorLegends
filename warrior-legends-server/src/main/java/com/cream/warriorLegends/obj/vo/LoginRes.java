package com.cream.warriorLegends.obj.vo;

import lombok.Data;

@Data
public class LoginRes {
    private final long id;
    private final String username;
    private final String token;

    public LoginRes(Long id, String username, String token) {
        this.id = id;
        this.username = username;
        this.token = token;
    }
}
