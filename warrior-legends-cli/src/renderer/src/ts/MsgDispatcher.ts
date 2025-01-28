type MsgListener = (msg) => void;

class MsgDispatcher {
  private msgType2Listeners: Map<number, MsgListener[]>;

  constructor() {
    this.msgType2Listeners = new Map();
  }

  addMsgListener(msgType: number, listener: MsgListener) {
    let listenerList = this.msgType2Listeners.get(msgType);
    if (!listenerList) {
      listenerList = [];
    }
    listenerList.push(listener);
  }

  dispatchMsg(msg) {
    const listenerList = this.msgType2Listeners.get(msg.msgType);
    if (!listenerList) {
      console.error("没有处理对应类型的回调");
      return;
    }
    for (const listener of listenerList) {
      listener(msg);
    }
  }

  sendMsg(msg) {
    window.electron.ipcRenderer.send("sendMsg", { msg });
  }
}

export default new MsgDispatcher();
