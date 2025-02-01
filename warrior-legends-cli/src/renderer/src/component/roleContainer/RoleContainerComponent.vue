<script setup lang="ts">
import { nextTick, onMounted, reactive } from "vue";
import Role from "@/interface/Role";
import { EnterMapRes, LoginMapRes } from "@/interface/res/ResInterface";
import msgReceiver from "@/net/ws/MsgReceiver";
import msgSender from "@/net/ws/MsgSender";
import { WalkReq } from "@/interface/req/ReqInterface";
import moveFunction from "@/component/common/moveFunction";

interface PropsType {
  metaInfo: LoginMapRes;
  mapDiv: HTMLElement;
}

const objMap = reactive<Map<string, Role>>(new Map());
const props = defineProps<PropsType>();
const metaInfo = props.metaInfo;
const self: Role = metaInfo.role;
//todo 将来抽取成外部配置的常量，包括父组件(GameMap组件)里的和主进程创建窗口用到的
const gridSize = 50;

msgReceiver.onReceiveEnterMap((enterMapRes: EnterMapRes) => {
  const enterRole = enterMapRes.role;
  objMap.set(enterRole.id, enterRole);
  nextTick(() => {
    computeOffsetOnce(enterRole);
  });
});

let canMove = true;

function addMoveKeyListener() {
  let lastMovingTime: number = 0;
  document.addEventListener("keydown", (e) => {
    const now = Date.now();
    if (now - lastMovingTime < 200 || !canMove) {
      // 200ms走一格
      return;
    }
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
    lastMovingTime = now;
    canMove = false;
    msgSender.sendWalk(walkReq);
  });
}

msgReceiver.onReceiveWalk((walkRes) => {
  const id = walkRes.id;
  const newX = walkRes.x;
  const newY = walkRes.y;
  const obj = objMap.get(id);
  obj!.xy.x = newX;
  obj!.xy.y = newY;
  if (id === self.id) {
    selfWalk(newX, newY);
  }
});

function selfWalk(newX: number, newY: number) {
  // 是移动自己，还是移动地图
  const oldX = self.xy.x;
  const oldY = self.xy.y;
  self.xy.x = newX;
  self.xy.y = newY;

  // 因为人物用的是相对定位，所以无论那种情况，人的位置都要移动
  const selfDiv = document.getElementById(self.id);
  moveFunction(newX, oldX, newY, oldY, true, selfDiv!).then(() => {
    canMove = true;
  });

  if (needMoveMap(newX, newY, oldX, oldY)) {
    // 移动地图
    moveFunction(newX, oldX, newY, oldY, false, props.mapDiv).then(() => {
      canMove = true;
    });
  }
}

// fixme 这个方法暂不考虑斜着走
function needMoveMap(
  newX: number,
  newY: number,
  oldX: number,
  oldY: number
): boolean {
  if (inXEdges(oldX)) {
    if (inAroundEdges(newX, newY)) {
      return false;
    }
    //动x
    if (newX !== oldX && inXEdges(newX)) {
      return false;
    }
  }
  if (inYEdges(oldY)) {
    if (inAroundEdges(newX, newY)) {
      return false;
    }
    //动y
    if (newY != oldY && inYEdges(newY)) {
      return false;
    }
  }
  return true;
}

function inAroundEdges(x: number, y: number): boolean {
  const xSize = x * gridSize + gridSize / 2;
  const ySize = y * gridSize + gridSize / 2;
  const mapWidthSize = metaInfo.width * gridSize;
  const mapHeightSize = metaInfo.height * gridSize;
  const winWidth = window.innerWidth;
  const winHeight = window.innerHeight;
  if (xSize < winWidth / 2 || xSize > mapWidthSize - winWidth / 2) {
    if (ySize < winHeight / 2 || ySize > mapHeightSize - winHeight / 2) {
      return true;
    }
  }
  return false;
}

function inXEdges(x: number): boolean {
  const xSize = x * gridSize + gridSize / 2;
  const mapWidthSize = metaInfo.width * gridSize;
  const winWidth = window.innerWidth;
  return xSize <= winWidth / 2 || xSize >= mapWidthSize - winWidth / 2;
}

function inYEdges(y: number): boolean {
  const ySize = y * gridSize + gridSize / 2;
  const mapHeightSize = metaInfo.height * gridSize;
  const winHeight = window.innerHeight;
  return ySize <= winHeight / 2 || ySize >= mapHeightSize - winHeight / 2;
}

onMounted(() => {
  addMoveKeyListener();
});

function computeOffsetOnce(role: Role) {
  const roleElement = document.getElementById(role.id);
  const left = role.xy.x * 50;
  const top = role.xy.y * 50;
  roleElement!.style.left = left + "px";
  roleElement!.style.top = top + "px";
}
</script>

<template>
  <div v-for="obj in objMap.values()" :id="obj.id" :key="obj.id" class="obj">
    x:{{ obj.xy.x }},y:{{ obj.xy.y }}
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
