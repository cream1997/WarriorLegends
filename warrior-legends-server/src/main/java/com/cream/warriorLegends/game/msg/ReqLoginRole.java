package com.cream.warriorLegends.game.msg;

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
public class ReqLoginRole extends MsgProcessor<Void> {

    private final RoleManager roleManager;
    private final MapManager mapManager;

    @Autowired
    public ReqLoginRole(RoleManager roleManager, MapManager mapManager) {
        this.roleManager = roleManager;
        this.mapManager = mapManager;
    }

    @Override
    public void process(long id) {
        Role role = roleManager.getRole(id);
        mapManager.loginMap(role);
    }

    @Override
    public ReqMsgType matchType() {
        return ReqMsgType.LoginRole;
    }
}
