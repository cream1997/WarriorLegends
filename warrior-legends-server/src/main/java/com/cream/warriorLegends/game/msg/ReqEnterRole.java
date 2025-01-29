package com.cream.warriorLegends.game.msg;

import com.cream.warriorLegends.game.msg.base.MsgProcessor;
import com.cream.warriorLegends.game.msg.base.ReqMsgType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ReqEnterRole extends MsgProcessor<Void> {
    @Override
    public void process(long id) {
        log.info("xx");
    }

    @Override
    public ReqMsgType matchType() {
        return ReqMsgType.EnterRole;
    }
}
