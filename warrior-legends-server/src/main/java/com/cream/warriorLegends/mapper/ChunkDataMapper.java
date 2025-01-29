package com.cream.warriorLegends.mapper;

import com.cream.warriorLegends.obj.entity.DataChunk;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author cream
 * Email:837800910@qq.com
 * 2025/1/27 21:43
 */
@Mapper
public interface ChunkDataMapper {

    DataChunk selectChunkData(@Param("tableName") String tableName, @Param("id") long id);

    void insertChunkData(@Param("tableName") String tableName, long id, byte[] data);

    void updateChunkData(@Param("tableName") String tableName, long id, byte[] data);
}
