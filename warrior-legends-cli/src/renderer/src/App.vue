<script setup lang="ts">

const ipcHandle = () => window.electron.ipcRenderer.send('ping')
</script>

<template>
    <div>
      <a target="_blank" rel="noreferrer" @click="ipcHandle">Send IPC</a>
    </div>
</template>
