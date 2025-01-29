package com.cream.warriorLegends.manager;

import com.cream.warriorLegends.game.base.Role;
import com.cream.warriorLegends.utils.bean.DbTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RoleManager {

    private final DbTool dbTool;

    @Autowired
    public RoleManager(DbTool dbTool) {
        this.dbTool = dbTool;
    }

    public Role getRole(long id) {
        Role role = this.dbTool.selectRole(id);
        if (role == null) {
            // fixme 快速创建角色，方便测试
            role = new Role();
            role.setId(id);
            role.setLevel(1);
            role.setHp(100);
            role.setMp(100);
            String randomStr = UUID.randomUUID().toString().replace("-", "").substring(0, 4);
            role.setNickNane("User@" + randomStr);
            dbTool.insertRole(role);
        }
        return role;
    }
}
