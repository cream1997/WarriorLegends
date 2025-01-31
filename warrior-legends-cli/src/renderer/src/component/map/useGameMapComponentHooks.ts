import { onMounted } from "vue";
import msgSender from "@/net/ws/MsgSender";

export default function useGameMapComponentHooks() {
  onMounted(() => {
    msgSender.sendLoginRole();
  });
}
