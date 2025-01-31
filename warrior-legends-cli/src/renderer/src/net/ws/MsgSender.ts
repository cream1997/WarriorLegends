import { WalkReq } from "@/interface/req/ReqInterface";

function sendMsg(msgType: ReqMsgType, data?: any) {
  window.electron.ipcRenderer.send("sendMsg", { msgType, data });
}

enum ReqMsgType {
  LoginRole = 1,
  Walk = 2
}

class MsgSender {
  sendLoginRole() {
    sendMsg(ReqMsgType.LoginRole);
  }

  sendWalk(walkReq: WalkReq) {
    sendMsg(ReqMsgType.Walk, walkReq);
  }
}

const msgSender = new MsgSender();
export default msgSender;
