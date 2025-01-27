package com.cream.warriorLegends.game.config;

import com.cream.warriorLegends.obj.common.position.Xy;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author cream
 * Email:837800910@qq.com
 * 2025/1/27 22:41
 */
@Getter
@Setter
public class MapCfg {
    private int id;
    private String name;
    private int width;
    private int height;
    private List<Xy> blockPoint;
}
