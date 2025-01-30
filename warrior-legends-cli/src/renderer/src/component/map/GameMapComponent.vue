<script lang="ts">
export default {
  name: "GameMapComponent"
};
</script>
<script setup lang="ts">
import { Role } from "@/interface/Role";
import { onMounted, reactive, ref } from "vue";
import useGameMapComponentHooks from "@/component/map/useGameMapComponentHooks";
import msgReceiver from "@/ts/MsgReceiver";

msgReceiver.onReceiveEnterMap((loginMapRes) => {
  console.log("收到LoginMapRes", loginMapRes);
});
useGameMapComponentHooks();

const me: Role = {
  id: "1",
  name: "test",
  x: 50,
  y: 50
};

const objList = reactive<Role[]>([me]);

const oneGridPx = 30;

const mapMeta = reactive({
  width: 25,
  height: 25
});

const mapRef = ref<HTMLDivElement>();

let self;

onMounted(() => {
  if (mapRef.value) {
    mapRef.value.style.width = mapMeta.width * oneGridPx + "px";
    mapRef.value.style.height = mapMeta.height * oneGridPx + "px";
  }
  fixSelf();
  moveMap();
  addMoveKeyListener();
});

function fixSelf() {
  self = document.getElementById("1");
  if (self) {
    self.style.left = `calc(50vw - ${oneGridPx / 2}px)`;
    self.style.top = `calc(50vh - ${oneGridPx / 2}px)`;
  }
}

function moveMap() {
  if (self) {
    const rect = self.getBoundingClientRect();
    const selfCenterLeft = rect.left + rect.width / 2;
    const selfCenterTop = rect.top + rect.height / 2;
    const mapLeft = selfCenterLeft - me.x;
    const mapTop = selfCenterTop - me.y;
    if (mapRef.value) {
      mapRef.value.style.left = mapLeft + "px";
      mapRef.value.style.top = mapTop + "px";
    }
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
</script>

<template>
  <div ref="mapRef" class="map">
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
  <div v-for="obj in objList" :id="obj.id" :key="obj.id" class="obj">
    {{ obj.name }}
  </div>
</template>

<style scoped>
.map {
  position: fixed;
  width: 80vw;
  height: 80vh;
  background: green;
}

.obj {
  position: fixed;
  display: inline-block;
  width: 30px;
  height: 30px;
  background: red;
  border: 1px solid black;
  border-radius: 50%;
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
