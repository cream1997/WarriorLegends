<script setup lang="ts">
import { onMounted, reactive } from "vue";
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

const emit = defineEmits<{
  (e: "moveMap", oldX: number, oldY: number, newX: number, newY: number): void;
}>();

function selfWalk(newX: number, newY: number) {
  // 是移动自己，还是移动地图
  const oldX = self.xy.x;
  const oldY = self.xy.y;
  self.xy.x = newX;
  self.xy.y = newY;
  let moveElement: HTMLElement;
  let moveRole: boolean;
  if (inAroundEdges(oldX, oldY) && inAroundEdges(newX, newY)) {
    // 移动自己
    moveElement = document.getElementById(self.id)!;
    moveRole = true;
  } else {
    // 移动地图
    moveElement = props.mapDiv;
    moveRole = false;
    // fixme delete
    emit("moveMap", oldX, oldY, newX, newY);
  }
  moveFunction(newX, oldX, newY, oldY, moveRole, moveElement).then(() => {
    canMove = true;
  });
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
