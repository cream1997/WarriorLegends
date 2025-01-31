package com.cream.warriorLegends.game.scene;

import com.cream.warriorLegends.game.base.Role;
import com.cream.warriorLegends.game.config.MapCfg;
import com.cream.warriorLegends.game.msg.dto.res.EnterMapRes;
import com.cream.warriorLegends.game.msg.dto.res.LoginMapRes;
import com.cream.warriorLegends.game.msg.dto.res.WalkRes;
import com.cream.warriorLegends.game.net.MsgDispatcher;
import com.cream.warriorLegends.obj.common.position.SquareRange;
import com.cream.warriorLegends.obj.common.position.Xy;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
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
                putPoint(xy, point);
            }
        }
    }

    private void putPoint(Xy xy, Point point) {
        if (this.allPoint.containsKey(xy)) {
            log.error("重复放置点位，mapId:{},mapName:{},xy:{}", this.id, this.name, xy);
            return;
        }
        this.allPoint.put(xy, point);
    }

    private Point getPointNoNull(Xy xy) {
        Point point = this.allPoint.get(xy);
        Objects.requireNonNull(point);
        return point;
    }

    private Role getRoleNoNull(long rid) {
        Role role = this.allRole.get(rid);
        Objects.requireNonNull(role);
        return role;
    }

    private void putRole(Role role) {
        if (allRole.containsKey(role.getId())) {
            log.error("{}进入地图时，地图{}中已经存在该玩家", role.getNickNane(), this.name);
        }
        this.allRole.put(role.getId(), role);
    }

    public void enterRole(Role role) {
        Point point = getPointNoNull(defaultBornRange.getCenterXy());
        putRole(role);
        point.addRole(role);
        role.setMapId(this.id);
        log.info("{}进入{}", role.getNickNane(), this.name);

        LoginMapRes loginMapRes = new LoginMapRes();
        loginMapRes.setRole(role);
        loginMapRes.setMapId(this.id);
        loginMapRes.setMapName(this.name);
        loginMapRes.setWidth(this.width);
        loginMapRes.setHeight(this.height);
        MsgDispatcher.sendMsg(role.getId(), loginMapRes);

        // todo enterRole信息要群发给周围的所有人
        EnterMapRes enterMapRes = new EnterMapRes();
        enterMapRes.setRole(role);
        MsgDispatcher.sendMsg(role.getId(), enterMapRes);
    }

    public void removeRole(long id) {
        Role role = getRoleNoNull(id);
        allRole.remove(id);
        Point point = getPointNoNull(role.getXy());
        point.removeRole(id);

        // todo 同步信息给周围人
    }

    public void walk(long id, Xy newXy) {
        Role role = getRoleNoNull(id);
        Xy oldXy = role.getXy();
        Objects.requireNonNull(oldXy);
        if (newXy.x < 0 || newXy.y < 0 || newXy.x > width - 1 || newXy.y > height - 1) {
            log.error("请求坐标错误，超出地图范围,id:{}", id);
            return;
        }
        if (Math.abs(newXy.x - oldXy.x) != 1 && Math.abs(newXy.y - oldXy.y) != 1) {
            log.error("请求坐标错误,id:{}", id);
            return;
        }
        Point oldPoint = getPointNoNull(role.getXy());
        oldPoint.removeRole(role.getId());
        Point newPoint = getPointNoNull(newXy);
        newPoint.addRole(role);
        // todo 同步消息给周围的人
        WalkRes walkRes = new WalkRes(id, newXy.x, newXy.y);
        MsgDispatcher.sendMsg(id, walkRes);
    }
}
