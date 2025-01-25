<script setup lang="ts">
import LoginPage from "@/pages/LoginPage.vue";

const ipcHandle = () => window.electron.ipcRenderer.send("ping");
</script>

<template>
  <LoginPage></LoginPage>
</template>
