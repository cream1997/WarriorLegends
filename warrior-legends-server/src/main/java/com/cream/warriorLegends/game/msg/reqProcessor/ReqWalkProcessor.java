package com.cream.warriorLegends.game.msg.reqProcessor;


import com.cream.warriorLegends.game.msg.base.MsgProcessor;
import com.cream.warriorLegends.game.msg.base.ReqMsgType;
import com.cream.warriorLegends.game.msg.dto.req.WalkReq;
import com.cream.warriorLegends.game.scene.GameMap;
import com.cream.warriorLegends.manager.MapManager;
import com.cream.warriorLegends.obj.common.position.Xy;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ReqWalkProcessor extends MsgProcessor<WalkReq> {

    private final MapManager mapManager;

    @Autowired
    public ReqWalkProcessor(MapManager mapManager) {
        this.mapManager = mapManager;
    }


    @Override
    public void process(long rid, @NonNull WalkReq data) {
        GameMap mapByRid = this.mapManager.getMapByRid(rid);
        mapByRid.walk(rid, new Xy(data.getX(), data.getY()));
    }

    @Override
    public ReqMsgType matchType() {
        return ReqMsgType.Walk;
    }
}
