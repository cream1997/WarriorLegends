package com.cream.warriorLegends.manager;

import com.cream.warriorLegends.game.config.MapCfg;
import com.cream.warriorLegends.game.scene.GameMap;
import com.cream.warriorLegends.obj.common.position.Xy;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component()
public class MapManager {

    private final ConcurrentHashMap<Integer, GameMap> allMap = new ConcurrentHashMap<>();

    private void addMap(int id, GameMap map) {
        allMap.put(id, map);
    }

    @PostConstruct
    public void init() {
        List<MapCfg> mapCfgs = mockCfgs();
        for (MapCfg mapCfg : mapCfgs) {
            GameMap gameMap = new GameMap(mapCfg);
            this.addMap(gameMap.getId(), gameMap);
            log.info("创建地图:{}", gameMap.getName());
        }
    }


    private List<MapCfg> mockCfgs() {
        List<MapCfg> mapCfgs = new ArrayList<>();
        MapCfg mapCfg = new MapCfg();
        mapCfg.setId(1);
        mapCfg.setName("精灵岛");
        mapCfg.setWidth(200);
        mapCfg.setHeight(200);
        List<Xy> blockPoints = new ArrayList<>();
        mapCfg.setBlockPoint(blockPoints);

        mapCfgs.add(mapCfg);
        return mapCfgs;
    }
}
