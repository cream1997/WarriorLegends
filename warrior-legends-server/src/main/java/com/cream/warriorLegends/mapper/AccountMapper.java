package com.cream.warriorLegends.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cream.warriorLegends.obj.entity.Account;
import lombok.NonNull;
import org.apache.ibatis.annotations.Param;

public interface AccountMapper extends BaseMapper<Account> {

    int selectCountByUsername(@NonNull @Param("username") String username);
}
