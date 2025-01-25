package com.cream.warriorLegends.service;

import com.cream.warriorLegends.obj.vo.LoginRes;
import lombok.NonNull;

public interface IAccountService {
    void register(@NonNull String username, @NonNull String password);

    LoginRes login(String username, String password);
}
