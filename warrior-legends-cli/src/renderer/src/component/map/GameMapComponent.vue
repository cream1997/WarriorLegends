<script lang="ts">
export default {
  name: "GameMapComponent"
};
</script>
<script setup lang="ts">
import { nextTick, onMounted, reactive, ref } from "vue";
import useGameMapComponentHooks from "@/component/map/useGameMapComponentHooks";
import msgReceiver from "@/net/ws/MsgReceiver";
import { EnterMapRes, LoginMapRes } from "@/interface/res/ResInterface";
import Role from "@/interface/Role";
import RoleContainerComponent from "@/component/roleContainer/RoleContainerComponent.vue";

useGameMapComponentHooks();

const gridSize = 50;

const mapMeta = ref<LoginMapRes>();
const mapRef = ref<HTMLDivElement>();
const roleContainer = ref();
let roleSelf: Role;

function initMap() {
  if (mapRef.value && mapMeta.value) {
    roleSelf = mapMeta.value.role;
    const mapWidth = mapMeta.value!.width * gridSize;
    const mapHeight = mapMeta.value!.height * gridSize;
    mapRef.value.style.width = mapWidth + "px";
    mapRef.value.style.height = mapHeight + "px";
    // 定位地图位置
    const roleX = (roleSelf.xy.x + 1) * gridSize - gridSize / 2;
    const roleY = (roleSelf.xy.y + 1) * gridSize - gridSize / 2;
    let leftOffset = window.innerWidth / 2 - roleX;
    let topOffset = window.innerHeight / 2 - roleY;
    if (leftOffset > 0) {
      leftOffset = 0;
    }
    if (leftOffset < -mapWidth) {
      leftOffset = -mapWidth;
    }
    if (topOffset > 0) {
      topOffset = 0;
    }
    if (topOffset < -mapHeight) {
      topOffset = -mapHeight;
    }
    mapRef.value.style.left = leftOffset + "px";
    mapRef.value.style.top = topOffset + "px";
  }
}

onMounted(() => {
  msgReceiver.onReceiveLoginMap((loginMapRes: LoginMapRes) => {
    mapMeta.value = loginMapRes;
    // 初始化地图
    nextTick(() => {
      initMap();
    });
  });
});
</script>

<template>
  <div v-if="mapMeta" ref="mapRef" class="map">
    <div
      v-for="(_ignore1, rowIndex) in Array.from({ length: mapMeta.height })"
      :key="rowIndex"
      class="row"
    >
      <div
        v-for="(_ignore2, columnIndex) in Array.from({
          length: mapMeta.width
        })"
        :key="columnIndex"
        class="grid"
        :style="{ width: gridSize + 'px', height: gridSize + 'px' }"
      ></div>
    </div>
    <role-container-component
      ref="roleContainer"
      :meta-info="mapMeta"
      :map-div="mapRef!"
    />
  </div>
</template>

<style scoped>
.map {
  position: fixed;
  width: 100vw;
  height: 100vh;
  background: green;
}

.row {
  /*行高为0，消除行间距*/
  line-height: 0;
}

.grid {
  display: inline-block;
  border: 1px solid black;
}
</style>
