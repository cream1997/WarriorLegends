package com.cream.warriorLegends.game.scene;

import com.cream.warriorLegends.game.base.Role;
import com.cream.warriorLegends.game.config.MapCfg;
import com.cream.warriorLegends.game.msg.dto.res.LoginMapRes;
import com.cream.warriorLegends.game.net.MsgDispatcher;
import com.cream.warriorLegends.obj.common.position.SquareRange;
import com.cream.warriorLegends.obj.common.position.Xy;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Getter
public class GameMap {
    private final int id;
    private final String name;
    private final int width;
    private final int height;
    private final SquareRange defaultBornRange;

    private final Map<Xy, Point> allPoint = new HashMap<>();
    private final ConcurrentHashMap<Long, Role> allRole = new ConcurrentHashMap<>();

    public GameMap(MapCfg mapCfg) {
        this.id = mapCfg.getId();
        this.name = mapCfg.getName();
        this.width = mapCfg.getWidth();
        this.height = mapCfg.getHeight();
        // todo range的作用补充
        this.defaultBornRange = new SquareRange(10, 10, 0);
        /*
         * 地图坐标左上角为原点
         */
        HashSet<Xy> blockXy = new HashSet<>(mapCfg.getBlockPoint());
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                Xy xy = new Xy(j, i);
                boolean isBlock = blockXy.contains(xy);
                Point point = new Point(j, i, isBlock);
                this.allPoint.put(xy, point);
            }
        }
    }

    public void enterRole(Role role) {
        if (allRole.containsKey(role.getId())) {
            log.error("{}进入地图时，地图{}中已经存在该玩家", role.getNickNane(), this.name);
        }
        Point point = allPoint.get(defaultBornRange.getCenterXy());
        if (point == null) {
            log.error("进入地图时找不到点位");
            return;
        }
        allRole.put(role.getId(), role);
        point.addRole(role.getId());
        log.info("{}进入{}", role.getNickNane(), this.name);

        LoginMapRes loginMapRes = new LoginMapRes();
        loginMapRes.setMapId(this.id);
        loginMapRes.setMapName(this.name);
        loginMapRes.setWidth(this.width);
        loginMapRes.setHeight(this.height);
        MsgDispatcher.sendMsg(role.getId(), loginMapRes);

        // todo enterRole信息要群发给周围的所有人
    }

    public void removeRole(long id) {
        Role role = allRole.get(id);
        if (role == null) {
            log.error("地图中不存在该玩家,id:{}", id);
            return;
        }
        allRole.remove(id);
        Point point = allPoint.get(role.getXy());
        if (point == null) {
            log.error("未找到对应点位");
            return;
        }
        point.removeRole(id);

        // todo 同步信息给周围人
    }
}
