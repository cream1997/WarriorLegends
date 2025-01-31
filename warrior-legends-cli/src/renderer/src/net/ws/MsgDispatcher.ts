type MsgListener = (data: any, msgType: number) => void;
import { ResMsgType } from "@/net/ws/MsgReceiver";

class MsgDispatcher {
  private msgType2Listeners: Map<ResMsgType, MsgListener[]>;

  constructor() {
    this.msgType2Listeners = new Map();
  }

  addMsgListener(msgType: ResMsgType, listener: MsgListener) {
    let listenerList = this.msgType2Listeners.get(msgType);
    if (!listenerList) {
      listenerList = [];
      this.msgType2Listeners.set(msgType, listenerList);
    }
    listenerList.push(listener);
  }

  dispatchMsg(msg) {
    console.log(`收到消息，msgType: ${msg.msgType}`);
    const listenerList = this.msgType2Listeners.get(msg.msgType);
    if (!listenerList) {
      console.error("没有处理对应类型的回调");
      return;
    }
    for (const listener of listenerList) {
      listener(msg.data, msg.msgType);
    }
  }
}

const msgDispatcher = new MsgDispatcher();
export default msgDispatcher;
