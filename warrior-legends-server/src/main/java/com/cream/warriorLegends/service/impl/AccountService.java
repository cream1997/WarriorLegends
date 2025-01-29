package com.cream.warriorLegends.service.impl;

import com.cream.warriorLegends.common.exception.TipErr;
import com.cream.warriorLegends.game.net.TokenValidator;
import com.cream.warriorLegends.mapper.AccountMapper;
import com.cream.warriorLegends.obj.entity.Account;
import com.cream.warriorLegends.obj.vo.LoginRes;
import com.cream.warriorLegends.service.IAccountService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService implements IAccountService {

    private final AccountMapper accountMapper;
    private final TokenValidator tokenValidator;

    @Autowired
    public AccountService(AccountMapper accountMapper, TokenValidator tokenValidator) {
        this.accountMapper = accountMapper;
        this.tokenValidator = tokenValidator;
    }

    @Override
    public void register(@NonNull String username, @NonNull String password) {
        int count = this.accountMapper.selectCountByUsername(username);
        if (count > 0) {// 查询username是否已注册
            throw new TipErr("用户名已存在");
        }
        // todo 密码加密
        Account account = new Account(username, password);
        this.accountMapper.insert(account);
    }

    @Override
    public LoginRes login(String username, String password) {
        Account account = this.accountMapper.selectByUsername(username);
        if (account == null) {
            throw new TipErr("用户名不存在");
        }
        if (!account.getPassword().equals(password)) {
            throw new TipErr("密码错误");
        }

        // todo 生成token
        String token = "xxxx";
        this.tokenValidator.setTokenCache(account.getId(), token);
        return new LoginRes(account.getId(), account.getUsername(), token);
    }
}
