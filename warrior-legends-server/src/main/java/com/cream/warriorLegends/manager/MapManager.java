package com.cream.warriorLegends.manager;

import com.cream.warriorLegends.game.base.Role;
import com.cream.warriorLegends.game.config.MapCfg;
import com.cream.warriorLegends.game.scene.GameMap;
import com.cream.warriorLegends.obj.common.position.Xy;
import com.cream.warriorLegends.utils.bean.DbTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class MapManager {

    private final ConcurrentHashMap<Integer, GameMap> allMap = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<Long, Role> allRole = new ConcurrentHashMap<>();

    private final GameMap mainCity;
    private final DbTool dbTool;

    public MapManager(DbTool dbTool) {
        this.dbTool = dbTool;
        this.init();
        this.mainCity = getMapNoNull(1);
        Objects.requireNonNull(this.mainCity);
    }

    public GameMap getMapNoNull(int mapId) {
        GameMap gameMap = this.allMap.get(mapId);
        Objects.requireNonNull(gameMap);
        return gameMap;
    }

    public void putRole(Role role) {
        allRole.put(role.getId(), role);
    }

    public Role getRole(long id) {
        return allRole.get(id);
    }

    public Role getRoleNoNull(long id) {
        Role role = allRole.get(id);
        Objects.requireNonNull(role);
        return role;
    }

    public void removeRole(long id) {
        allRole.remove(id);
    }

    private void init() {
        List<MapCfg> mapCfgs = mockCfgs();
        for (MapCfg mapCfg : mapCfgs) {
            GameMap gameMap = new GameMap(mapCfg);
            this.addMap(gameMap.getId(), gameMap);
            log.info("创建地图:{}", gameMap.getName());
        }
    }

    public GameMap getMapByRid(long rid) {
        Role role = getRoleNoNull(rid);
        return getMapNoNull(role.getMapId());
    }

    private void addMap(int id, GameMap map) {
        allMap.put(id, map);
    }

    private List<MapCfg> mockCfgs() {
        List<MapCfg> mapCfgs = new ArrayList<>();
        MapCfg mapCfg = new MapCfg();
        mapCfg.setId(1);
        mapCfg.setName("精灵岛");
        mapCfg.setWidth(25);
        mapCfg.setHeight(25);
        List<Xy> blockPoints = new ArrayList<>();
        mapCfg.setBlockPoint(blockPoints);

        mapCfgs.add(mapCfg);
        return mapCfgs;
    }

    public void loginMap(Role role) {
        // fixme 进入默认地图
        putRole(role);
        mainCity.enterRole(role);
    }

    public void logout(long id) {
        Role role = getRole(id);
        if (role == null) {
            // 这里允许为空，因为拒绝连接也会走到这里
            return;
        }
        mainCity.removeRole(role.getId());
        removeRole(role.getId());
        this.dbTool.updateRole(role);
        log.info("{}退出游戏", role.getNickName());
    }
}
