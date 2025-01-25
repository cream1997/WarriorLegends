package com.cream.warriorLegends.service.impl;

import com.cream.warriorLegends.common.exception.TipErr;
import com.cream.warriorLegends.mapper.AccountMapper;
import com.cream.warriorLegends.obj.entity.Account;
import com.cream.warriorLegends.service.IAccountService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService implements IAccountService {

    private final AccountMapper accountMapper;

    @Autowired
    public AccountService(AccountMapper accountMapper) {
        this.accountMapper = accountMapper;
    }

    @Override
    public void register(@NonNull String username, @NonNull String password) {
        int count = accountMapper.selectCountByUsername(username);
        if (count > 0) {// 查询username是否已注册
            throw new TipErr("用户名已存在");
        }
        // todo 密码加密
        Account account = new Account(username, password);
        accountMapper.insert(account);
    }
}
