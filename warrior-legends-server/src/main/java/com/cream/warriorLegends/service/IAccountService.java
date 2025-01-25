package com.cream.warriorLegends.service;

import lombok.NonNull;

public interface IAccountService {
    void register(@NonNull String username, @NonNull String password);
}
