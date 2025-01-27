package com.cream.warriorLegends.game.scene;

import com.cream.warriorLegends.game.base.Role;
import com.cream.warriorLegends.game.config.MapCfg;
import com.cream.warriorLegends.obj.common.position.Xy;
import lombok.Getter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class GameMap {
    private final int id;
    private final String name;
    private final int width;
    private final int height;

    private final Map<Xy, Point> allPoint = new HashMap<>();
    private final ConcurrentHashMap<Long, Role> allRole = new ConcurrentHashMap<>();

    public GameMap(MapCfg mapCfg) {
        this.id = mapCfg.getId();
        this.name = mapCfg.getName();
        this.width = mapCfg.getWidth();
        this.height = mapCfg.getHeight();
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
}
