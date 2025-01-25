package com.cream.warriorLegends.controller;

import com.cream.warriorLegends.service.IAccountService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/account")
public class AccountController {

    private final IAccountService accountService;

    @Autowired
    public AccountController(IAccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/register")
    public void register(@NonNull String username, @NonNull String password){
        this.accountService.register(username, password);
    }

    @PostMapping("/login")
    public void login(){}
}
