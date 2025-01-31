<script setup lang="ts">
import { onMounted, reactive } from "vue";
import Role from "@/interface/Role";
import { EnterMapRes, LoginMapRes } from "@/interface/res/ResInterface";
import msgReceiver from "@/net/ws/MsgReceiver";
import msgSender from "@/net/ws/MsgSender";
import { WalkReq } from "@/interface/req/ReqInterface";

interface PropsType {
  metaInfo: LoginMapRes;
}

const props = defineProps<PropsType>();
const metaInfo = props.metaInfo;
const self: Role = metaInfo.role;
const objList = reactive<Role[]>([]);
//todo 将来抽取成外部配置的常量，包括父组件(GameMap组件)里的和主进程创建窗口用到的
const gridSize = 50;

msgReceiver.onReceiveEnterMap((enterMapRes: EnterMapRes) => {
  const enterRole = enterMapRes.role;
  objList.push(enterRole);
});

function moveMap() {
  if (self) {
    // const rect = self.getBoundingClientRect();
    // const selfCenterLeft = rect.left + rect.width / 2;
    // const selfCenterTop = rect.top + rect.height / 2;
    // const mapLeft = selfCenterLeft - me.x;
    // const mapTop = selfCenterTop - me.y;
    // if (mapRef.value) {
    //   mapRef.value.style.left = mapLeft + "px";
    //   mapRef.value.style.top = mapTop + "px";
    // }
  }
}

let canMove = true;

function addMoveKeyListener() {
  let lastMovingTime: number = 0;
  document.addEventListener("keydown", (e) => {
    const now = Date.now();
    if (now - lastMovingTime < 200 || !canMove) {
      // 200ms走一格
      return;
    }
    lastMovingTime = now;
    canMove = false;
    const walkReq: WalkReq = { x: self.xy.x, y: self.xy.y };
    // 判断按的是上下左右
    if (e.key === "ArrowUp") {
      walkReq.y -= 1;
    } else if (e.key === "ArrowDown") {
      walkReq.y += 1;
    } else if (e.key === "ArrowLeft") {
      walkReq.x -= 1;
    } else if (e.key === "ArrowRight") {
      walkReq.x += 1;
    }
    if (
      walkReq.x < 0 ||
      walkReq.y < 0 ||
      walkReq.x > metaInfo.width - 1 ||
      walkReq.y > metaInfo.height - 1
    ) {
      return;
    }
    msgSender.sendWalk(walkReq);
  });
}

msgReceiver.onReceiveWalk((walkRes) => {
  const id = walkRes.id;
  const newX = walkRes.x;
  const newY = walkRes.y;
  if (id === self.id) {
    selfWalk(newX, newY);
  }
});

function moveSelf(newX: number, oldX: number, newY: number, oldY: number) {
  const selfDiv = document.getElementById(self.id);
  //200ms移动一格子（50px）;1ms 0.25px; 4ms 1px; 20ms 5px
  let leftNeedAdd = (newX - oldX) * gridSize;
  let topNeedAdd = (newY - oldY) * gridSize;
  const onceLeftNeedAdd = (newX - oldX) * 5;
  const onceTopNeedAdd = (newY - oldY) * 5;
  const selfMoveTimer = setInterval(() => {
    if (selfDiv) {
      const nowLeft = selfDiv.offsetLeft;
      const nowTop = selfDiv.offsetTop;
      if (leftNeedAdd !== 0) {
        selfDiv!.style.left = nowLeft + onceLeftNeedAdd + "px";
        leftNeedAdd -= onceLeftNeedAdd;
      }
      if (topNeedAdd !== 0) {
        selfDiv!.style.top = nowTop + onceTopNeedAdd + "px";
        topNeedAdd -= onceTopNeedAdd;
      }
      console.log(leftNeedAdd, topNeedAdd);
      if (leftNeedAdd === 0 && topNeedAdd === 0) {
        clearInterval(selfMoveTimer);
        canMove = true;
      }
    }
  }, 20);
}

function selfWalk(newX: number, newY: number) {
  // 是移动自己，还是移动地图
  const oldX = self.xy.x;
  const oldY = self.xy.y;
  if (inAroundEdges(oldX, oldY) && inAroundEdges(newX, newY)) {
    // 移动自己
    moveSelf(newX, oldX, newY, oldY);
  } else {
    moveSelf(newX, oldX, newY, oldY);
    // moveMap();
  }
  self.xy.x = newX;
  self.xy.y = newY;
}

function inAroundEdges(x: number, y: number): boolean {
  const xSize = (x + 1) * gridSize;
  const ySize = (y + 1) * gridSize;
  const mapWidthSize = metaInfo.width * gridSize;
  const mapHeightSize = metaInfo.height * gridSize;
  const winWidth = window.innerWidth;
  const winHeight = window.innerHeight;
  if (xSize <= winWidth || xSize >= mapWidthSize - winWidth) {
    if (ySize <= winHeight || ySize >= mapHeightSize - winHeight) {
      return true;
    }
  }
  return false;
}

onMounted(() => {
  addMoveKeyListener();
});

function computeOffset(role: Role) {
  const left = role.xy.x * 50;
  const top = role.xy.y * 50;
  return {
    left: left + "px",
    top: top + "px"
  };
}
</script>

<template>
  <div
    v-for="obj in objList"
    :id="obj.id"
    :key="obj.id"
    class="obj"
    :style="computeOffset(obj)"
  >
    {{ obj.nickName }}
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
