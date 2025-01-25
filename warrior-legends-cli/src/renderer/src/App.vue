<script setup lang="ts">

const ipcHandle = () => window.electron.ipcRenderer.send("ping");
</script>

<template>
  <router-view v-slot="{ Component }">
    <keep-alive>
      <component :is="Component" />
    </keep-alive>
  </router-view>
</template>
