// todo 抽取常量（主进程创建窗口、地图组件，role容器组件等都用到了这个魔数）
const moveOneGridMs = 200;
const gridSize = 50;
const onePxNeedMs = moveOneGridMs / gridSize;
const onceMovePx = 5;
const timeout = onceMovePx * onePxNeedMs;
const moveCount = gridSize / onceMovePx;

export default function moveFunction(
  newX: number,
  oldX: number,
  newY: number,
  oldY: number,
  moveRole: boolean,
  moveElement: HTMLElement
) {
  let leftNeedAdd = (newX - oldX) * gridSize;
  let topNeedAdd = (newY - oldY) * gridSize;
  if (!moveRole) {
    // 不是移动角色，那就是移动地图
    leftNeedAdd = -leftNeedAdd;
    topNeedAdd = -topNeedAdd;
  }
  const onceLeftNeedAdd = leftNeedAdd / moveCount;
  const onceTopNeedAdd = topNeedAdd / moveCount;
  return new Promise<void>((resolve, reject) => {
    const moveTimer = setInterval(() => {
      const nowLeft = moveElement.offsetLeft;
      const nowTop = moveElement.offsetTop;
      if (leftNeedAdd !== 0) {
        moveElement.style.left = nowLeft + onceLeftNeedAdd + "px";
        leftNeedAdd -= onceLeftNeedAdd;
      }
      if (topNeedAdd !== 0) {
        moveElement.style.left = nowTop + onceTopNeedAdd + "px";
        topNeedAdd -= onceTopNeedAdd;
      }
      if (leftNeedAdd === 0 && topNeedAdd === 0) {
        clearInterval(moveTimer);
        resolve();
      }
    }, timeout);
  });
}
