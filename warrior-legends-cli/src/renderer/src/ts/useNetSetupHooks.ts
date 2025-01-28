import { onMounted, onUnmounted } from "vue";
import msgDispatcher from "@/ts/MsgDispatcher";

export default function useNetSetupHooks() {
  onMounted(() => {
    window.electron.ipcRenderer.on("wsMsg", (event, msg) => {
      msgDispatcher.dispatchMsg(msg);
    });
  });

  onUnmounted(() => {
    window.electron.ipcRenderer.removeAllListeners("wsMsg");
  });
}
