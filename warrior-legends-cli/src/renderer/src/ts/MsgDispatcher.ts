type MsgListener = (msg: any) => void;
import { ResMsgType } from "@/ts/MsgReceiver";

class MsgDispatcher {
  private msgType2Listeners: Map<ResMsgType, MsgListener[]>;

  constructor() {
    this.msgType2Listeners = new Map();
  }

  addMsgListener(msgType: ResMsgType, listener: MsgListener) {
    console.log("执行");
    let listenerList = this.msgType2Listeners.get(msgType);
    if (!listenerList) {
      listenerList = [];
      this.msgType2Listeners.set(msgType, listenerList);
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
}

const msgDispatcher = new MsgDispatcher();
export default msgDispatcher;
