package com.cream.warriorLegends;

import com.cream.warriorLegends.mapper.ChunkDataMapper;
import com.cream.warriorLegends.obj.entity.DataChunk;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WarriorLegendsServerAppTests {

    @Autowired
    private ChunkDataMapper chunkDataMapper;

	@Test
	void contextLoads() {
	}

    @Test
    void testSelectChunk(){
        DataChunk tRole = chunkDataMapper.selectChunkData("t_role", 1);
        System.out.println(tRole);
    }

}
