package com.cream.warriorLegends.utils.bean;

import com.alibaba.fastjson2.JSON;
import com.cream.warriorLegends.mapper.ChunkDataMapper;
import com.cream.warriorLegends.game.base.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * @author cream
 * Email:837800910@qq.com
 * 2025/1/27 22:01
 */
@Component
public class DbTool {

    private final ChunkDataMapper chunkDataMapper;

    @Autowired
    public DbTool(ChunkDataMapper chunkDataMapper) {
        this.chunkDataMapper = chunkDataMapper;
    }

    public void insertRole(Role role) {
        insertChunkData("t_role", role.getId(), role);
    }

    public Role selectRole(long id) {
        return selectChunkData("t_role", id, Role.class);
    }

    public void updateRole(Role role) {
        updateChunkData("t_role", role.getId(), role);
    }

    private void insertChunkData(String tableName, long id, Object obj) {
        byte[] data = format(obj);
        chunkDataMapper.insertChunkData(tableName, id, data);
    }

    private <T> T selectChunkData(String tableName, long id, Class<T> clazz) {
        byte[] data = chunkDataMapper.selectChunkData(tableName, id);
        return parse(data, clazz);
    }

    private void updateChunkData(String tableName, long id, Object obj) {
        byte[] data = format(obj);
        chunkDataMapper.updateChunkData(tableName, id, data);
    }

    private byte[] format(Object obj) {
        return JSON.toJSONString(obj).getBytes(StandardCharsets.UTF_8);
    }

    private <T> T parse(byte[] data, Class<T> tClass) {
        String jsonStr = new String(data, StandardCharsets.UTF_8);
        return JSON.parseObject(jsonStr, tClass);
    }
}
