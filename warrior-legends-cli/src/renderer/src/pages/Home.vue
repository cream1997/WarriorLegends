<script setup lang="ts">
import useNetSetupHooks from "@/net/ws/useNetSetupHooks";
import GameMapComponent from "@/component/map/GameMapComponent.vue";

useNetSetupHooks();
</script>

<template>
  <GameMapComponent></GameMapComponent>
</template>

<style scoped></style>
