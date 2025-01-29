function sendMsg(msgType: ReqMsgType, data?: any) {
  window.electron.ipcRenderer.send("sendMsg", { msgType, data });
}

enum ReqMsgType {
  LoginRole = 1
}

class MsgSender {
  sendLoginRole() {
    sendMsg(ReqMsgType.LoginRole);
  }
}

const msgSender = new MsgSender();
export default msgSender;
