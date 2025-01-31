<script setup lang="ts">
import { onMounted, reactive } from "vue";
import { Role } from "@/interface/Role";

let self;
const me: Role = {
  id: "1",
  name: "test",
  x: 50,
  y: 50
};

const objList = reactive<Role[]>([me]);

function moveMap() {
  if (self) {
    const rect = self.getBoundingClientRect();
    const selfCenterLeft = rect.left + rect.width / 2;
    const selfCenterTop = rect.top + rect.height / 2;
    const mapLeft = selfCenterLeft - me.x;
    const mapTop = selfCenterTop - me.y;
    // if (mapRef.value) {
    //   mapRef.value.style.left = mapLeft + "px";
    //   mapRef.value.style.top = mapTop + "px";
    // }
  }
}

let moving = false;

function addMoveKeyListener() {
  document.addEventListener("keydown", (e) => {
    if (moving) {
      return;
    }
    // 判断按的是上下左右
    let xAdd = 0;
    let yAdd = 0;
    if (e.key === "ArrowUp") {
      yAdd -= 1;
    } else if (e.key === "ArrowDown") {
      yAdd += 1;
    } else if (e.key === "ArrowLeft") {
      xAdd -= 1;
    } else if (e.key === "ArrowRight") {
      xAdd += 1;
    }

    moving = true;
    // 0.2s走一格300ms走30px
    let moveLength = 0;
    const task = setInterval(() => {
      moveLength += 1;
      me.x += xAdd;
      me.y += yAdd;
      moveMap();
      if (moveLength >= 30) {
        clearInterval(task);
        moving = false;
      }
    }, 10);
  });
}

function fixSelf() {
  self = document.getElementById("1");
  if (self) {
    // self.style.left = `calc(50vw - ${oneGridPx / 2}px)`;
    // self.style.top = `calc(50vh - ${oneGridPx / 2}px)`;
  }
}

onMounted(() => {
  fixSelf();
  moveMap();
  addMoveKeyListener();
});
</script>

<template>
  <div v-for="obj in objList" :id="obj.id" :key="obj.id" class="obj">
    {{ obj.name }}
  </div>
</template>

<style scoped>
.obj {
  position: absolute;
  display: inline-block;
  width: 50px;
  height: 50px;
  background: red;
  border: 1px solid black;
  border-radius: 50%;
}
</style>
