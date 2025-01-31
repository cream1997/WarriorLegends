package com.cream.warriorLegends.game.msg.reqProcessor;

import com.cream.warriorLegends.game.base.Role;
import com.cream.warriorLegends.game.msg.base.MsgProcessor;
import com.cream.warriorLegends.game.msg.base.ReqMsgType;
import com.cream.warriorLegends.manager.MapManager;
import com.cream.warriorLegends.manager.RoleManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ReqLoginRoleProcessor extends MsgProcessor<Void> {

    private final RoleManager roleManager;
    private final MapManager mapManager;

    @Autowired
    public ReqLoginRoleProcessor(RoleManager roleManager, MapManager mapManager) {
        this.roleManager = roleManager;
        this.mapManager = mapManager;
    }

    @Override
    public void process(long rid) {
        Role role = roleManager.getRole(rid);
        mapManager.loginMap(role);
    }

    @Override
    public ReqMsgType matchType() {
        return ReqMsgType.LoginRole;
    }
}
