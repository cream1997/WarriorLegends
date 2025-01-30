<script lang="ts">
export default {
  name: "GameMapComponent"
};
</script>
<script setup lang="ts">
import { nextTick, onMounted, reactive, ref } from "vue";
import useGameMapComponentHooks from "@/component/map/useGameMapComponentHooks";
import msgReceiver from "@/ts/MsgReceiver";
import { LoginMapRes } from "@/interface/res/LoginMapRes";

useGameMapComponentHooks();

const oneGridPx = 36;

const mapMeta = ref<LoginMapRes | null>();
const mapRef = ref<HTMLDivElement>();

onMounted(() => {
  msgReceiver.onReceiveEnterMap((loginMapRes: LoginMapRes) => {
    mapMeta.value = loginMapRes;
    // 初始化地图
    nextTick(() => {
      if (mapRef.value) {
        mapRef.value.style.width = mapMeta.value!.width * oneGridPx + "px";
        mapRef.value.style.height = mapMeta.value!.height * oneGridPx + "px";
      }
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
      ></div>
    </div>
  </div>
</template>

<style scoped>
.map {
  position: fixed;
  width: 80vw;
  height: 80vh;
  background: green;
}

.row {
  /*行高为0，消除行间距*/
  line-height: 0;
}

.grid {
  display: inline-block;
  width: 30px;
  height: 30px;
  border: 1px solid black;
}
</style>
