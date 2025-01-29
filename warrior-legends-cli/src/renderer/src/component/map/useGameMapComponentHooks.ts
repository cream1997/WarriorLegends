import { onMounted } from "vue";
import msgSender from "@/ts/MsgSender";

export default function useGameMapComponentHooks() {
  onMounted(() => {
    msgSender.sendLoginRole();
  });
}
