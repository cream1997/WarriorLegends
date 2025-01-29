package com.cream.warriorLegends.utils.bean;

import com.alibaba.fastjson2.JSON;
import com.cream.warriorLegends.mapper.ChunkDataMapper;
import com.cream.warriorLegends.game.base.Role;
import com.cream.warriorLegends.obj.entity.DataChunk;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Slf4j
@Data
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
        DataChunk dataChunk = chunkDataMapper.selectChunkData(tableName, id);
        if (dataChunk == null) {
            log.error("查询数据为空,tableName:{},id:{}", tableName, id);
            return null;
        }
        byte[] data = dataChunk.getData();
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
