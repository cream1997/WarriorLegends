<script setup lang="ts">
const ipcHandle = () => window.electron.ipcRenderer.send("ping");
import useWinMetaStore from "@/store/useWinMetaStore";

const winMetaStore = useWinMetaStore();

window.electron.ipcRenderer.on(
  "windowMeta",
  (event, { rowCount, columnCount, gridSize, winWidth, winHeight }) => {
    winMetaStore.init(rowCount, columnCount, gridSize, winWidth, winHeight);
  }
);
</script>

<template>
  <router-view v-slot="{ Component }">
    <keep-alive>
      <component :is="Component" />
    </keep-alive>
  </router-view>
</template>
